package com.ahmad.aqarmaptask.remote

import com.ahmad.aqarmaptask.utils.NetworkUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    val loggingInterceptor:HttpLoggingInterceptor by lazy{
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    val retrofitBuilder: Retrofit.Builder by lazy{
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.themoviedb.org/documentation/api")
                .client(okHttpClient)
    }

    val mainApi: MainApi by lazy {
        retrofitBuilder
                .build()
                .create(MainApi::class.java)
    }
}