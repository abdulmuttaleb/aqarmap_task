package com.ahmad.aqarmaptask.remote

import com.ahmad.aqarmaptask.remote.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {

    @GET("/discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(): Response<BaseResponse>
}