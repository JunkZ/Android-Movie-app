package com.ltu.m7019edemoapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ltu.m7019edemoapp.domain.MovieModel
import com.ltu.m7019edemoapp.model.Movie

@Dao
interface VideoDao {
    @Query("select * from movies")
    fun getVideos(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( movies: List<Movie>)

    @Query("delete from movies")
    fun deleteAll()
}



@Database(entities = [DatabaseMovie::class,Movie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract val videoDao: VideoDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context): MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MovieDatabase::class.java,
                "movies").build()
        }
    }
    return INSTANCE
}