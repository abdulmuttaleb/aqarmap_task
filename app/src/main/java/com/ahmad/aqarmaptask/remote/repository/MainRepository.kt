package com.ahmad.aqarmaptask.remote.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.ahmad.aqarmaptask.MyApplication
import com.ahmad.aqarmaptask.model.dao.MovieDao
import com.ahmad.aqarmaptask.model.database.MovieRoomDatabase
import com.ahmad.aqarmaptask.remote.MainApi
import com.ahmad.aqarmaptask.remote.Resource
import com.ahmad.aqarmaptask.remote.RetrofitBuilder
import com.ahmad.aqarmaptask.remote.response.BaseResponse
import com.ahmad.aqarmaptask.remote.response.PopularMoviesResponse
import com.ahmad.aqarmaptask.utils.NetworkUtils.API_KEY
import com.ahmad.aqarmaptask.utils.NetworkUtils.getMessageFromErrorBody
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response
import javax.inject.Inject

class MainRepository(context: Context) {
    private var getPopularMoviesJob: CompletableJob? = null
    private var moviesDao: MovieDao
    init {
        val moviesDatabase = MovieRoomDatabase.getInstance(context)
        moviesDao = moviesDatabase.movieDao()
    }
    fun getPopularMovies(page:Int): LiveData<Resource<Response<PopularMoviesResponse>>>{
        getPopularMoviesJob = Job()
        return object : LiveData<Resource<Response<PopularMoviesResponse>>>(){
            override fun onActive() {
                super.onActive()
                getPopularMoviesJob?.let { taskJob ->
                    CoroutineScope(IO + taskJob).launch {
                        try{
                            withContext(Main){
                                value = Resource.loading()
                            }

                            val fetchPopularMoviesResponse = RetrofitBuilder.mainApi.getPopularMovies(
                                API_KEY,page)

                            if(fetchPopularMoviesResponse.isSuccessful){
                                fetchPopularMoviesResponse.body()?.results?.let {
                                    moviesDao.insertMovies(
                                        it
                                    )
                                }
                            }
                            withContext(Main) {
                                value = if (fetchPopularMoviesResponse.isSuccessful) {
                                    Resource.success(fetchPopularMoviesResponse)
                                } else {
                                    Resource.error(getMessageFromErrorBody(fetchPopularMoviesResponse.errorBody()))
                                }
                            }
                        }catch (e: Exception){
                            withContext(Main){
                                value = Resource.error("Exception: ${e.localizedMessage}")
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

    companion object {
        private const val TAG = "MainRepository"
    }

}