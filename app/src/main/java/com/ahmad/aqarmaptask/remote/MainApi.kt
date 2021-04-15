package com.ahmad.aqarmaptask.remote

import com.ahmad.aqarmaptask.remote.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey:String, @Query("page") page:Int?): Response<BaseResponse>
}