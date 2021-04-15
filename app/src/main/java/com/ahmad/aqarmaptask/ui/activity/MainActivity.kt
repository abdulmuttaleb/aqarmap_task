package com.ahmad.aqarmaptask.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ahmad.aqarmaptask.R
import com.ahmad.aqarmaptask.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initObservers()

        mainViewModel.setGetPopularMovies(1)
    }

    fun initObservers(){
        mainViewModel.popularMovies.observe(this, {
            Log.e(TAG, "initObservers: ${it.data?.body()}")
        })
    }
}