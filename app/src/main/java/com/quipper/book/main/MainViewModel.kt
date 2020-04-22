package com.quipper.book.main

import androidx.lifecycle.ViewModel
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.domain.LoadLocalPopular
import com.quipper.book.model.Movie
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(private val useCase: GetPopularUseCase, private val loadLocalPopular: LoadLocalPopular) : ViewModel() {

    private val publishSubject: PublishSubject<MainIntent> = PublishSubject.create()
    private val viewEffectSubject: PublishSubject<MainViewEffect> = PublishSubject.create()
    private val compositeDisposable = CompositeDisposable()

    // filter intent
    private val intentFilter = ObservableTransformer<MainIntent, MainIntent> { intentObservable ->
        intentObservable.publish { intents ->
            Observable.merge(
                listOf(
                    intents.ofType(MainIntent.LoadPopularMovieIntent::class.java).take(1)
                )
            )
                .cast(MainIntent::class.java)
                .mergeWith(
                    intents.filter {
                        it is MainIntent.LoadPopularMovieIntent
                    }

                )
                .cast(MainIntent::class.java)
        }

    }

    private fun intentToAction(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.LoadPopularMovieIntent -> {
                MainAction.LoadPopularMovieAction(intent.apiKey)
            }
            is MainIntent.LoadLocalPopularMovieIntent -> {
                MainAction.LoadLocalPopularMovieAction
            }
        }
    }

    private val loadPopularProcess =
        ObservableTransformer<MainAction.LoadPopularMovieAction, MainResult.GetPopularResult> { actions ->
            actions.flatMap { action ->
                useCase.execute(action.apiKey)
                    .toObservable()
                    .map { popular ->
                        MainResult.GetPopularResult.Success(popular)
                    }
                    .cast(MainResult.GetPopularResult::class.java)
                    .startWith(MainResult.GetPopularResult.IsLoading)
                    .onErrorReturn(MainResult.GetPopularResult::Failed)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }

        }

    private val loadLocalPopularProcess =
        ObservableTransformer<MainAction.LoadLocalPopularMovieAction, MainResult.LoadLocalResult> { actions ->
            actions.flatMap {
                loadLocalPopular.execute()
                    .toObservable()
                    .map { lists ->
                        MainResult.LoadLocalResult.Success(lists)
                    }
                    .cast(MainResult.LoadLocalResult::class.java)
                    .onErrorReturn(MainResult.LoadLocalResult::Failed)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }

        }

    private val actionProcessor = ObservableTransformer<MainAction, MainResult> { actions ->
        actions.publish { actionObservable ->
            Observable.merge(
                listOf(
                    actionObservable.ofType(MainAction.LoadPopularMovieAction::class.java).compose(
                        loadPopularProcess
                    ),
                    actionObservable.ofType(MainAction.LoadLocalPopularMovieAction::class.java).compose(
                        loadLocalPopularProcess
                    )
                )
            )
                .cast(MainResult::class.java)
                .mergeWith(
                    actionObservable.filter {
                        it !is MainAction.LoadPopularMovieAction &&
                                it !is MainAction.LoadLocalPopularMovieAction
                    }.flatMap {
                        Observable.error<MainResult>(Throwable("wrong intent"))
                    }
                )
        }

    }

    private fun reducer(previousState: MainState, result: MainResult): MainState {
        return when (result) {
            is MainResult.GetPopularResult.IsLoading -> {
                previousState.copy(isLoading = true)
            }
            is MainResult.GetPopularResult.Success -> {
                previousState.copy(isLoading = false, movies = result.popular.results)
            }
            is MainResult.GetPopularResult.Failed -> {
                previousState.copy(isLoading = false, isError = true)
            }
            is MainResult.LoadLocalResult.Success -> {
                if (result.list.isEmpty()) {
                    previousState.copy(isLoading = true, movies = result.list)
                } else {
                    previousState.copy(isLoading = false, movies = result.list)
                }
            }
            is MainResult.LoadLocalResult.Failed -> {
                previousState.copy(isLoading = false, isError = true)
            }
        }
    }

    private val stateObservable = publishSubject
        .compose(intentFilter) // filter intent
        .map(this::intentToAction) // intent to action
        .compose(actionProcessor) // run process
        .doOnNext(this::handleEffect) // view effect if necessary
        .scan(MainState.default(), this::reducer) // reducer
        .distinctUntilChanged() // reduce duplicate state
        .replay(1) // maintain last one
        .autoConnect(0) // enable to subscribe by all subscriber

    fun getState(): Observable<MainState> {
        return stateObservable
    }

    fun getViewEffect(): Observable<MainViewEffect> {
        return viewEffectSubject
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun processIntent(intentObservable: Observable<MainIntent>) {
        intentObservable.subscribe(publishSubject)
    }

    private fun handleEffect(result: MainResult) {
        if (result is MainResult.GetPopularResult.Failed) {
            viewEffectSubject.onNext(MainViewEffect.ShowToastError)
        }
    }
}

data class MainState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val isRecentLoading: Boolean = false,
    val isRecentError: Boolean = false,
    val recent: List<Movie> = emptyList(),
    val buttonRefreshEnabled: Boolean = false
) {
    companion object {
        fun default(): MainState {
            return MainState()
        }
    }
}
