package com.quipper.book.domain

import com.quipper.book.model.Movie
import com.quipper.book.model.Popular
import io.reactivex.Single

interface LoadLocalPopular {

    fun execute() : Single<List<Movie>>
}
