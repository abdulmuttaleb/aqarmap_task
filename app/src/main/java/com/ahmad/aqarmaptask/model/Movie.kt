package com.ahmad.aqarmaptask.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "movies")
data class Movie (

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    var id:Int? = 0,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title:String? = null,

    @SerializedName("poster_path")
    @ColumnInfo(name = "posterPath")
    var posterPath:String? = null,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String? = null,

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    var overview:String? = null,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Float? = null
):Serializable