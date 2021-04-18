package com.ahmad.aqarmaptask.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.ahmad.aqarmaptask.R
import com.ahmad.aqarmaptask.databinding.FragmentMovieDetailsBinding
import com.ahmad.aqarmaptask.model.Movie
import com.ahmad.aqarmaptask.utils.NetworkUtils
import com.squareup.picasso.Picasso
import org.joda.time.DateTime


class MovieDetailsFragment : DialogFragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var passedMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        if(dialog != null && dialog?.window != null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)

        passedMovie = arguments?.getSerializable("passedMovie") as Movie

        Log.e(TAG, "onViewCreated: $passedMovie")

        initFragment(passedMovie)
    }

    fun initFragment(passedMovie: Movie){
        binding.tvMovieTitle.text = passedMovie.title
        binding.tvOverview.text = passedMovie.overview
        binding.tvOverview.movementMethod = ScrollingMovementMethod()
        binding.tvDate.text = DateTime.parse(passedMovie.releaseDate).toString("dd/MMM/yyyy")
        Picasso.get()
            .load(NetworkUtils.IMAGES_BASE_URL.plus(passedMovie.posterPath))
            .resize(300, 400)
            .centerCrop()
            .into(binding.ivMoviePoster)
        binding.tvRating.text = passedMovie.voteAverage.toString().plus(" /10")

    }
    companion object {
        const val TAG = "MovieDetailsFragment"
    }
}