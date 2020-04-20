package com.quipper.book.repository

import com.quipper.book.model.Popular
import com.quipper.book.network.ApiService
import com.quipper.book.network.RetrofitClient
import io.reactivex.Single

class PopularRepositoryImpl(private val apiService: ApiService) : PopularRepository {
    override fun getPopular(): Single<Popular> {
        return apiService.getPopular(RetrofitClient.API_KEY)
    }
}
