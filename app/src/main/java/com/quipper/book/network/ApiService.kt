package com.quipper.book.network

import com.quipper.book.model.Popular
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("3/movie/popular?")
    fun getPopular(@Query("api_key") api_key: String): Single<Popular>
}
