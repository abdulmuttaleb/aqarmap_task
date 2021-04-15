package com.ahmad.aqarmaptask.remote

sealed class Resource<T>(
        val status: Status? = null,
        val data: T? = null,
        val message:String? = null
){
    class success<T>(data:T): Resource<T>(Status.SUCCESS,data)
    class loading<T>(data:T? = null): Resource<T>(Status.LOADING, data)
    class error<T>(message: String? = "", data: T? = null): Resource<T>(Status.ERROR, data, message)

    enum class Status{SUCCESS, ERROR, LOADING}
}