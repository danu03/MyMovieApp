package com.danusuhendra.mymovieapp.di

import com.danusuhendra.mymovieapp.network.ApiInterface
import com.danusuhendra.mymovieapp.repository.DetailRepository
import com.danusuhendra.mymovieapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(apiInterface: ApiInterface) : MainRepository {
        return MainRepository(apiInterface)
    }

    @Singleton
    @Provides
    fun provideDetailRepository(apiInterface: ApiInterface) : DetailRepository {
        return DetailRepository(apiInterface)
    }
}