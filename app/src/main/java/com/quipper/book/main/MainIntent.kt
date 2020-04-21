package com.quipper.book.main

sealed class MainIntent {

    data class LoadPopularMovieIntent(val apiKey: String) : MainIntent()
}
