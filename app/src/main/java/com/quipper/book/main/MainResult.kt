package com.quipper.book.main

import com.quipper.book.model.Movie
import com.quipper.book.model.Popular

sealed class MainResult {

    sealed class GetPopularResult : MainResult() {

        object IsLoading : GetPopularResult()

        data class Success(val popular: Popular) : GetPopularResult()

        data class Failed(val error: Throwable) : GetPopularResult()

    }

    sealed class LoadLocalResult : MainResult() {

        data class Success(val list: List<Movie>) : LoadLocalResult()

        data class Failed(val error: Throwable) : LoadLocalResult()
    }
}
