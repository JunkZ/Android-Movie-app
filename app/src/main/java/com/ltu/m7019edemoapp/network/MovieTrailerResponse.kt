package com.ltu.m7019edemoapp.network


import com.ltu.m7019edemoapp.model.MovieTrailer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieTrailerResponse {
    @Json(name = "id")
    var id: Long = 0

    @Json(name = "results")
    var results: List<MovieTrailer> = listOf()
}