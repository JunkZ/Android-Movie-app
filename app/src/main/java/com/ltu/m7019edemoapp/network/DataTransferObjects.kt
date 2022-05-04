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

package com.ltu.m7019edemoapp.network

import com.ltu.m7019edemoapp.database.DatabaseMovie
import com.ltu.m7019edemoapp.database.MovieList
import com.ltu.m7019edemoapp.database.Movies
import com.ltu.m7019edemoapp.domain.MovieModel
import com.ltu.m7019edemoapp.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 * @see domain package for
 */

/**
 * VideoHolder holds a list of Videos.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "videos": []
 * }
 */
@JsonClass(generateAdapter = true)
data class MovieContainer(val movies: List<NetworkVideo>)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkVideo(    val id: Long,
                            val title: String,
                            val poster_Path: String,
                            val backdrop_Path: String,
                            val releaseDate: String,
                            val overview: String) {
}


/**
 * Convert Network results to database objects
 */
//fun MovieContainer.asDomainModel(): List<MovieModel> {
//    return movies.map {
//        MovieModel(
//            page = it.page,
//            results = it.results,
//            total_pages = it.total_pages,
//            total_results = it.total_results
//        )
//    }
//}
////
/////**
//// * Convert Network results to database objects
//// */
//fun MovieDetailResponse.asDatabaseModel(): List<Movie> {
//    return movies.map {
//        Movie(
//            id = it.id,
//            title = it.title,
//            poster_Path = it.poster_Path,
//            backdrop_Path = it.backdrop_Path,
//            releaseDate = it.releaseDate,
//            overview = it.overview
//            page = it.page,
//            results = it.results,
//            total_pages = it.total_pages,
//            total_results = it.total_results)
    //}}

////            id = it.id,
////
////            title = it.title,
////            poster_Path = it.poster_Path,
////            backdrop_Path = it.backdrop_Path,
////            releaseDate = it.releaseDate,
////            overview = it.overview)
//    }
//}



