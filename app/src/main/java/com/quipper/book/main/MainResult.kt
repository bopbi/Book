package com.quipper.book.main

import com.quipper.book.model.Popular

sealed class MainResult {

    sealed class GetPopularResult : MainResult() {

        object IsLoading : GetPopularResult()

        data class Success(val popular: Popular) : GetPopularResult()

        data class Failed(val error: Throwable) : GetPopularResult()

    }
}
