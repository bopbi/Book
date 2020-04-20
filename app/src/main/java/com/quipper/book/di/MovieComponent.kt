package com.quipper.book.di

import com.quipper.book.MainActivity
import dagger.Component

@Component(modules = [(MovieModul::class)])
interface MovieComponent {
    fun inject(activity: MainActivity)
}
