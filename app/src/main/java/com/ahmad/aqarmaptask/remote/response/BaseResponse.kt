package com.ahmad.aqarmaptask.remote.response

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import retrofit2.Response

open class BaseResponse {

    @SerializedName("message")
    @Expose
    var message:String? = null

    @SerializedName("error")
    @Expose
    var error: MutableMap<String, ArrayList<String>> = mutableMapOf()

    @SerializedName("code")
    @Expose
    var code:Int? = null

    @SerializedName("status")
    @Expose
    var status:String? = null
}