package com.quipper.book.di

import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.domain.GetPopularUseCaseImpl
import com.quipper.book.network.ApiService
import com.quipper.book.network.RetrofitClient
import com.quipper.book.repository.PopularRepository
import com.quipper.book.repository.PopularRepositoryImpl
import dagger.Module
import dagger.Provides

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
