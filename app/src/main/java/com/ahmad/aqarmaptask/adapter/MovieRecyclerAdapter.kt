package com.ahmad.aqarmaptask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmad.aqarmaptask.databinding.ItemMovieBinding
import com.ahmad.aqarmaptask.model.Movie
import com.ahmad.aqarmaptask.utils.NetworkUtils
import com.squareup.picasso.Picasso
import org.joda.time.DateTime

class MovieRecyclerAdapter: RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    var moviesList: ArrayList<Movie> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class MovieViewHolder(val itemMovieBinding: ItemMovieBinding): RecyclerView.ViewHolder(itemMovieBinding.root){
        fun bind(movie: Movie){
            itemMovieBinding.tvMovieTitle.text = movie.title
            itemMovieBinding.tvRating.text = movie.voteAverage.toString()
//            itemMovieBinding.tvOverview.text = movie.overview
//            itemMovieBinding.tvDate.text = DateTime.parse(movie.releaseDate).toString("dd/MMM/yyyy")
            Picasso.get()
                    .load(NetworkUtils.IMAGES_BASE_URL.plus(movie.posterPath))
                    .resize(300, 400)
                    .centerCrop()
                    .into(itemMovieBinding.ivMoviePoster)
        }
    }

}