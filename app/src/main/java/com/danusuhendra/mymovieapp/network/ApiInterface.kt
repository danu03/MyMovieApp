package com.danusuhendra.mymovieapp.network

import com.danusuhendra.mymovieapp.model.DetailResponse
import com.danusuhendra.mymovieapp.model.GenreResponse
import com.danusuhendra.mymovieapp.model.ListMovieByGenreResponse
import com.danusuhendra.mymovieapp.utils.DETAIL
import com.danusuhendra.mymovieapp.utils.GENRE
import com.danusuhendra.mymovieapp.utils.GENRE_LIST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET(GENRE)
    suspend fun getGenre(
        @Query("api_key") apiKey: String
    ) : GenreResponse

    @GET(GENRE_LIST)
    suspend fun getListByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") withGenre: Int = 28
    ) : ListMovieByGenreResponse

    @GET(DETAIL)
    suspend fun getDetailMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String
    ) : DetailResponse
}