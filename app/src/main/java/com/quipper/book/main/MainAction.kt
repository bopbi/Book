package com.quipper.book.main

sealed class MainAction {

    data class LoadPopularMovieAction(val apiKey: String) : MainAction()

    object LoadLocalPopularMovieAction : MainAction()
}
