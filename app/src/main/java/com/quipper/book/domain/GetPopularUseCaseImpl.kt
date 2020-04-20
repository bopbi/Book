package com.quipper.book.domain

import com.quipper.book.model.Popular
import com.quipper.book.repository.PopularRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetPopularUseCaseImpl(private val popularRepository: PopularRepository) : GetPopularUseCase {
    override fun execute(): Single<Popular> {
        return popularRepository.getPopular()
    }
}
