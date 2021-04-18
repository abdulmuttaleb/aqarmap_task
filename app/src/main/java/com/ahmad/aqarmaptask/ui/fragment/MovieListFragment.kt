package com.ahmad.aqarmaptask.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmad.aqarmaptask.R
import com.ahmad.aqarmaptask.adapter.MovieRecyclerAdapter
import com.ahmad.aqarmaptask.databinding.FragmentMovieListBinding
import com.ahmad.aqarmaptask.model.Movie
import com.ahmad.aqarmaptask.remote.Resource
import com.ahmad.aqarmaptask.ui.activity.BaseActivity
import com.ahmad.aqarmaptask.viewmodel.MainViewModel

class MovieListFragment : Fragment(), MovieRecyclerAdapter.MovieItemClickListener {

    private var _binding:FragmentMovieListBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController

    lateinit var mainViewModel: MainViewModel

    lateinit var movieRecyclerAdapter: MovieRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        //todo initialize everything here

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        initFragment()

        initObservers()
    }

    fun initFragment(){

        val gridLayoutManager = GridLayoutManager(requireContext(),
                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    2
                }else{
                    4
                }
        )
        binding.rvMovies.layoutManager = gridLayoutManager

        movieRecyclerAdapter = MovieRecyclerAdapter()
        movieRecyclerAdapter.setMovieItemClickListener(this)

        binding.rvMovies.adapter = movieRecyclerAdapter

        mainViewModel.setGetPopularMovies(1)

    }

    fun initObservers() {
        mainViewModel.popularMovies.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.error -> {
                    Toast.makeText(requireContext(), "There was an error fetching movies", Toast.LENGTH_SHORT).show()
                }
                is Resource.loading -> {
                    //todo loading
                }
                is Resource.success -> {
                    val moviesList = resource.data?.body()?.results
                    if (moviesList != null) {
                        movieRecyclerAdapter.moviesList = moviesList
                    }
                }
            }
        })
    }

    override fun onMovieItemClicked(movie: Movie) {
//        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(passedMovie = movie)
//        navController.navigate(action)
        val movieDetailFragment = MovieDetailsFragment()
        val args = Bundle()
        args.putSerializable("passedMovie", movie)
        movieDetailFragment.arguments = args

        movieDetailFragment.show(parentFragmentManager, "movieDetails")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "MovieListFragment"
    }
}