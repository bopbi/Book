package com.quipper.book.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val URL: String = "https://api.themoviedb.org/"
    const val API_KEY: String = "eb7fda7d2094a7d74a933e33ed24e7d4"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

}

