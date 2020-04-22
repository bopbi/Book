package com.quipper.book.main

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.model.Movie
import com.quipper.book.model.Popular
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var mockedGetPopularUseCase: GetPopularUseCase

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }

        mainViewModel = MainViewModel(mockedGetPopularUseCase)
    }

    @Test
    fun `LoadPopularMovieIntent should call getPopularUseCase and emit result when use case emit data`() {
        val expectedKey = RandomString.make(2)
        val expectedData = Popular(10, listOf(Movie(1.0, true, RandomString.make(2))))

        val testObserver = mainViewModel.getState().test()

        whenever(mockedGetPopularUseCase.execute(any())).thenReturn(Single.just(expectedData))

        mainViewModel.processIntent(Observable.just(MainIntent.LoadPopularMovieIntent(expectedKey)))

        testObserver.assertValueAt(1) { state ->
            state.isLoading && !state.isError && state.movies.isEmpty()
        }

        testObserver.assertValueAt(2) { state ->
            !state.isLoading && !state.isError && state.movies.isNotEmpty()
        }
    }
}
