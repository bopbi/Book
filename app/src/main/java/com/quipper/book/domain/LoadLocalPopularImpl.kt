package com.quipper.book.domain

import android.content.SharedPreferences
import com.quipper.book.model.Movie
import com.quipper.book.repository.PopularRepository
import io.reactivex.Single

class LoadLocalPopularImpl(private val popularRepository: PopularRepository) : LoadLocalPopular {
    override fun execute(): Single<List<Movie>> {
        return popularRepository.loadLocalPopular()
    }
}
