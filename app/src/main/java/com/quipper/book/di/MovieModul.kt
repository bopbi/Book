package com.quipper.book.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.domain.GetPopularUseCaseImpl
import com.quipper.book.network.ApiService
import com.quipper.book.network.RetrofitClient
import com.quipper.book.repository.PopularRepository
import com.quipper.book.repository.PopularRepositoryImpl
import dagger.MapKey
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class MovieModul{
    @Provides
    fun provideApiService(): ApiService{
        return RetrofitClient.apiService
    }
    @Provides
    fun providePopularRepository(apiService: ApiService): PopularRepository{
        return PopularRepositoryImpl(apiService)
    }

    @Provides
    fun provideGetPopularUseCase(popularRepository: PopularRepository): GetPopularUseCase{
        return GetPopularUseCaseImpl(popularRepository)
    }
}
