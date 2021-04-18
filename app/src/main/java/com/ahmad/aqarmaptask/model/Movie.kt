package com.ahmad.aqarmaptask.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Movie (
    @SerializedName("title")
    var title:String? = null,

    @SerializedName("poster_path")
    var posterPath:String? = null,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("overview")
    var overview:String? = null,

    @SerializedName("vote_average")
    var voteAverage: Float? = null
):Serializable