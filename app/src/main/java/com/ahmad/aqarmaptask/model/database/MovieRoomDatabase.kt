package com.ahmad.aqarmaptask.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmad.aqarmaptask.model.Movie
import com.ahmad.aqarmaptask.model.dao.MovieDao

@Database(entities = [Movie::class], version = 1)
abstract class MovieRoomDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object{
        private var instance : MovieRoomDatabase? = null
        fun getInstance(context: Context): MovieRoomDatabase = instance ?: synchronized(this){
            instance ?: Room.databaseBuilder(
                context,
                MovieRoomDatabase::class.java,
                "movies_database").build()
                .also {
                    instance = it
                }
        }
    }
}