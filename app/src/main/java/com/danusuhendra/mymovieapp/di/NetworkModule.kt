package com.danusuhendra.mymovieapp.di

import com.danusuhendra.mymovieapp.network.ApiInterface
import com.danusuhendra.mymovieapp.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson
    ) : Retrofit.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit.Builder): ApiInterface {
        return retrofit
            .build()
            .create(ApiInterface::class.java)
    }
}