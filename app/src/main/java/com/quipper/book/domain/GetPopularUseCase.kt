package com.quipper.book.domain

import com.quipper.book.model.Popular
import io.reactivex.Single

interface GetPopularUseCase {
    fun execute(apiKey: String): Single<Popular>
}
