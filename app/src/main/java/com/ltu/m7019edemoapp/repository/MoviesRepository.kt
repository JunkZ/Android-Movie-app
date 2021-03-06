/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ltu.m7019edemoapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ltu.m7019edemoapp.database.MovieDatabase
import com.ltu.m7019edemoapp.database.asDomainModel
import com.ltu.m7019edemoapp.domain.MovieModel


import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.network.MovieResponseDAO
import com.ltu.m7019edemoapp.network.RemoteDatabase
import com.ltu.m7019edemoapp.network.TMDBApi

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */

class MovieResponseRepository(private val database: MovieDatabase) {

//    val videos: LiveData<List<Movie>> = Transformations.map(database.videoDao.getVideos()) {
//        it.asDomainModel()
//    }
    val videos = database.videoDao.getVideos()
    //val videos2 = database.videoDao.getVideos2()
    //val videos = database.videoDao.getVideos().value
    val xd = 5+5
    //val xs = xd+5
    //suspend fun getAllResponseRepositories() = database.getAllMovieRepositories()

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshVideos(Popular:Boolean) {
        withContext(Dispatchers.IO) {
            Timber.d("refresh videos is called");

            database.videoDao.deleteAll()
            val videospop = if(Popular){
                TMDBApi.movieListRetrofitService.getPopularMovies()
            } else {
                TMDBApi.movieListRetrofitService.getTopRatedMovies()
            }
            //val videospop = RemoteDatabase.provideMovieResponse.getAllMovieRepositories()
            database.videoDao.insertAll(videospop.results)
            val f = database
            val d = f.videoDao
        }
    }

}