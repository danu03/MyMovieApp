package com.danusuhendra.mymovieapp.repository

import com.danusuhendra.mymovieapp.model.GenreResponse
import com.danusuhendra.mymovieapp.model.ListMovieByGenreResponse
import com.danusuhendra.mymovieapp.network.ApiInterface
import com.danusuhendra.mymovieapp.utils.API_KEY
import com.danusuhendra.mymovieapp.utils.DataState
import com.danusuhendra.mymovieapp.utils.SORT_BY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository(
    private val apiInterface: ApiInterface,
) {

    suspend fun getGenre() : Flow<DataState<GenreResponse>> = flow {
        emit(DataState.Loading)
        try {
            val networkGenre = apiInterface.getGenre(API_KEY)
            emit(DataState.Success(networkGenre))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getListMovieByGenre(genreId: Int) : Flow<DataState<ListMovieByGenreResponse>> = flow {
        emit(DataState.Loading)
        try {
            val listMovieByGenre = apiInterface.getListByGenre(API_KEY, genreId)
            emit(DataState.Success(listMovieByGenre))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}