package com.ltu.m7019edemoapp.network


import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.model.MovieTrailer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieResponse {
    @Json(name = "page")
    var page: Int = 0

    @Json(name = "results")
    var results: List<Movie> = listOf()

    @Json(name = "total_pages")
    var total_pages: Int = 0

    @Json(name = "total_results")
    var total_results: Int = 0
}