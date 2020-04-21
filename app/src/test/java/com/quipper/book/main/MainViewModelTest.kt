package com.quipper.book.main

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before

class MainViewModelTest {

    private lateinit var

    @Before
    fun setup() {

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler {  Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler {  Schedulers.trampoline() }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler {  Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler {  Schedulers.trampoline() }

    }
}
