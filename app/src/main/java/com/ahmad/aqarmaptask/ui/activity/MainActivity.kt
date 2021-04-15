package com.ahmad.aqarmaptask.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ahmad.aqarmaptask.R
import com.ahmad.aqarmaptask.databinding.ActivityMainBinding
import com.ahmad.aqarmaptask.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initObservers()

        mainViewModel.setGetPopularMovies(1)
    }

    fun initObservers(){
        mainViewModel.popularMovies.observe(this, {
            Log.e(TAG, "initObservers: ${it.data?.body()?.results.toString()}")
        })
    }
}