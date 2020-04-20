package com.quipper.book.di

import com.quipper.book.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(MovieModul::class), (ViewModelModule::class)])
interface MovieComponent {
    fun inject(activity: MainActivity)
}
