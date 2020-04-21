package com.quipper.book.repository

import com.quipper.book.model.Popular
import io.reactivex.Single

interface PopularRepository {
    fun getPopular(apiKey: String): Single<Popular>
}
