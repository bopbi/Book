package com.quipper.book.main

import androidx.lifecycle.ViewModel
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(private val useCase: GetPopularUseCase) : ViewModel() {

    private val publishSubject: PublishSubject<MainState> = PublishSubject.create()
    private val compositeDisposable = CompositeDisposable()
    private val state = publishSubject
        .replay(1)
        .autoConnect(0)

    fun initializeData() {
        val disposable = useCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .map {
                MainState(
                    isLoading = false,
                    isError = false,
                    movies = it.results
                )
            }
            .startWith(
                MainState(
                    isLoading = true,
                    isError = false,
                    movies = emptyList()
                )
            )
            .subscribe({
                publishSubject.onNext(it)
            }, {
                val mainState = MainState(
                    isLoading = false,
                    isError = true,
                    movies = emptyList()
                )
                publishSubject.onNext(mainState)
            })
        compositeDisposable.add(disposable)
    }

    fun getState(): Observable<MainState> {
        return state
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

data class MainState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val isRecentLoading: Boolean = false,
    val isRecentError: Boolean = false,
    val recent: List<Movie> = emptyList(),
    val buttonRefreshEnabled : Boolean = false
)
