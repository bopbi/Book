package com.quipper.book.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quipper.book.model.Movie
import com.quipper.book.model.Popular
import com.quipper.book.network.ApiService
import com.quipper.book.network.RetrofitClient
import io.reactivex.Single

class PopularRepositoryImpl(private val apiService: ApiService, private val sharedPreferences: SharedPreferences) : PopularRepository {
    override fun getPopular(apiKey: String): Single<Popular> {
        return apiService.getPopular(apiKey)
            .doOnError {
                Log.i(">>>>", "Error")
            }
            .doOnSuccess {
                Log.i(">>>>", "Success")
            }
            .map {
                val stringsToSave = Gson().toJson(it.results)
                sharedPreferences.edit().putString("RESULT", stringsToSave)
                it
            }
    }

    override fun loadLocalPopular(): Single<List<Movie>> {
        return Single.defer {
            val stringsFromLocal = sharedPreferences.getString("RESULT", "[]")
            val gson = Gson()
            val itemType = object : TypeToken<List<Movie>>() {}.type
            val movieList = gson.fromJson<List<Movie>>(stringsFromLocal, itemType)
            Single.just(movieList)
        }
    }
}
