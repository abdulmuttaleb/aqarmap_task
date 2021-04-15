package com.ahmad.aqarmaptask.remote.response

import com.ahmad.aqarmaptask.model.Movie
import com.google.gson.annotations.SerializedName

class PopularMoviesResponse : BaseResponse() {

    @SerializedName("page")
    var page: Int? = null

    @SerializedName("results")
    var results: ArrayList<Movie>? = null

    @SerializedName("total_results")
    var totalResults: Int? = null

    @SerializedName("total_pages")
    var totalPages: Int? = null

}