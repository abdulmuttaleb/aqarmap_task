package com.ahmad.aqarmaptask.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmad.aqarmaptask.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<Movie>
}