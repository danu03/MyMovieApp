package com.danusuhendra.mymovieapp.repository

import com.danusuhendra.mymovieapp.model.DetailResponse
import com.danusuhendra.mymovieapp.network.ApiInterface
import com.danusuhendra.mymovieapp.utils.API_KEY
import com.danusuhendra.mymovieapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailRepository(
    private val apiInterface: ApiInterface,
) {
    suspend fun getDetail(movieId: Int): Flow<DataState<DetailResponse>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(apiInterface.getDetailMovie(movieId, API_KEY)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}