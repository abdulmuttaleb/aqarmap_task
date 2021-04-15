package com.ahmad.aqarmaptask.utils

import com.ahmad.aqarmaptask.BuildConfig
import okhttp3.ResponseBody
import org.json.JSONObject

object NetworkUtils {
    const val BASE_URL:String = "https://api.themoviedb.org/3/"
    const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    const val API_KEY:String = BuildConfig.API_KEY
    fun getMessageFromErrorBody(errorBody: ResponseBody?): String{
        val jsonObjectError = JSONObject(errorBody!!.string())
        return jsonObjectError.getString("message")
    }
}