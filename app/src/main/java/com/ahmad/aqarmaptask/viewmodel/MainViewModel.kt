package com.ahmad.aqarmaptask.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.ahmad.aqarmaptask.remote.Resource
import com.ahmad.aqarmaptask.remote.repository.MainRepository
import com.ahmad.aqarmaptask.remote.response.BaseResponse
import com.ahmad.aqarmaptask.remote.response.PopularMoviesResponse
import com.ahmad.aqarmaptask.utils.AndroidUtils.observeOnce
import retrofit2.Response

class MainViewModel(context: Context): ViewModel() {

    private val getPopularMoviesRequest : MutableLiveData<Int> = MutableLiveData()

    val mainRepository = MainRepository(context)

    val popularMovies: LiveData<Resource<Response<PopularMoviesResponse>>> = getPopularMoviesRequest.switchMap {
        mainRepository.getPopularMovies(it)
    }

    fun setGetPopularMovies(page:Int){
        getPopularMoviesRequest.value = page
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.cancelJobs()
    }

}