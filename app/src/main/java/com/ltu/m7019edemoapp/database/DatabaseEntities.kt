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

package com.ltu.m7019edemoapp.database

import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ltu.m7019edemoapp.domain.MovieModel
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.model.MovieDetail
import com.ltu.m7019edemoapp.model.MovieGenre
import com.squareup.moshi.Json


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */


/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Long,
    val title: String,
    val poster_Path: String,
    val backdrop_Path: String,
    val releaseDate: String,
    val overview: String)

//    val page: Int,
//    val results: Int,
//    val total_pages: Int,
//    val total_results: Int

//    val id: Long,
//    val title: String,
//    val poster_Path: String,
//    val backdrop_Path: String,
//    val releaseDate: String,
//    val overview: String)
//fun List<Movie>.asDomainModel(): List<Movie> {
//    return map {
//        Movie(
//            page = it.page,
//            results = it.results,
//            total_pages = it.total_pages,
//            total_results = it.total_results)
//    }
//}


fun List<Movie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            poster_Path = it.poster_Path,
            backdrop_Path = it.backdrop_Path,
            releaseDate = it.releaseDate,
            overview = it.overview
        )
    }
}

/**
 * Map DatabaseVideos to domain entities
 */
//fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
//    return map {
//        Movie(
//            id = it.id,
//            title = it.title,
//            poster_Path = it.poster_Path,
//            backdrop_Path = it.backdrop_Path,
//            releaseDate = it.releaseDate,
//            overview = it.overview)
//
////            title = it.title,
////            posterPath = it.posterPath,
////            backdrop_path = it.backdropPath,
////            release_date = it.releaseDate,
////            overview = it.overview
////
////            url = it.url,
////            title = it.title,
////            description = it.description,
////            updated = it.updated,
////            thumbnail = it.thumbnail)
//    }
//}