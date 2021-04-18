package com.ahmad.aqarmaptask.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.ahmad.aqarmaptask.model.Movie
import com.ahmad.aqarmaptask.model.dao.MovieDao
import com.ahmad.aqarmaptask.model.database.MovieRoomDatabase
import com.ahmad.aqarmaptask.remote.Resource
import com.ahmad.aqarmaptask.remote.repository.MainRepository
import com.ahmad.aqarmaptask.remote.response.BaseResponse
import com.ahmad.aqarmaptask.remote.response.PopularMoviesResponse
import com.ahmad.aqarmaptask.utils.AndroidUtils.observeOnce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel(context: Context): ViewModel() {

    private var moviesDao: MovieDao
    init {
        val moviesDatabase = MovieRoomDatabase.getInstance(context)
        moviesDao = moviesDatabase.movieDao()
    }

    init {
        fetchSavedMovies()
    }

    private val getPopularMoviesRequest : MutableLiveData<Int> = MutableLiveData()

    val popularMoviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()

    val mainRepository = MainRepository(moviesDao)

    val popularMovies: LiveData<Resource<Response<PopularMoviesResponse>>> = getPopularMoviesRequest.switchMap {
        mainRepository.getPopularMovies(it)
    }

    fun setGetPopularMovies(page:Int){
        getPopularMoviesRequest.value = page
    }

    fun fetchSavedMovies(){
        var movies: ArrayList<Movie>?

        CoroutineScope(Dispatchers.IO).launch {
            val fetchedMovies = moviesDao.getMovies()
            movies = ArrayList(fetchedMovies)
            withContext(Main){
                popularMoviesList.value = movies
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.cancelJobs()
    }

    companion object{
        const val TAG = "MainViewModel"
    }

}