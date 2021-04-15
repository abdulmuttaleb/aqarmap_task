package com.ahmad.aqarmaptask.remote.repository

import androidx.lifecycle.LiveData
import com.ahmad.aqarmaptask.remote.Resource
import com.ahmad.aqarmaptask.remote.RetrofitBuilder
import com.ahmad.aqarmaptask.remote.response.BaseResponse
import com.ahmad.aqarmaptask.utils.NetworkUtils.API_KEY
import com.ahmad.aqarmaptask.utils.NetworkUtils.getMessageFromErrorBody
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

object MainRepository {

    private var getPopularMoviesJob: CompletableJob? = null

    fun getPopularMovies(page:Int): LiveData<Resource<Response<BaseResponse>>>{
        getPopularMoviesJob = Job()
        return object : LiveData<Resource<Response<BaseResponse>>>(){
            override fun onActive() {
                super.onActive()
                getPopularMoviesJob?.let { taskJob ->
                    CoroutineScope(IO + taskJob).launch {
                        withContext(Main){
                            value = Resource.loading()
                        }

                        val fetchPopularMoviesResponse = RetrofitBuilder.mainApi.getPopularMovies(
                            API_KEY,page)

                        withContext(Main) {
                            value = if (fetchPopularMoviesResponse.isSuccessful) {
                                Resource.success(fetchPopularMoviesResponse)
                            } else {
                                Resource.error(getMessageFromErrorBody(fetchPopularMoviesResponse.errorBody()))
                            }
                        }
                    }
                    taskJob.complete()
                }
            }
        }
    }

    fun cancelJobs(){
        getPopularMoviesJob?.cancel()
    }

    private const val TAG = "MainRepository"

}