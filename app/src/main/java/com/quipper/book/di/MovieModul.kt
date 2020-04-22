package com.quipper.book.di

import android.content.Context
import android.content.SharedPreferences
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.domain.GetPopularUseCaseImpl
import com.quipper.book.domain.LoadLocalPopular
import com.quipper.book.domain.LoadLocalPopularImpl
import com.quipper.book.network.ApiService
import com.quipper.book.network.RetrofitClient
import com.quipper.book.repository.PopularRepository
import com.quipper.book.repository.PopularRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class MovieModul(private val context: Context) {

    @Provides
    fun provideSharedPreference() : SharedPreferences {
        return context.getSharedPreferences("", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.apiService
    }

    @Provides
    fun providePopularRepository(apiService: ApiService, sharedPreferences: SharedPreferences): PopularRepository {
        return PopularRepositoryImpl(apiService, sharedPreferences)
    }

    @Provides
    fun provideGetPopularUseCase(popularRepository: PopularRepository): GetPopularUseCase {
        return GetPopularUseCaseImpl(popularRepository)
    }

    @Provides
    fun provideLoadLocalPopular(popularRepository: PopularRepository): LoadLocalPopular {
        return LoadLocalPopularImpl(popularRepository)
    }
}
