package com.ltu.m7019edemoapp.network


import com.ltu.m7019edemoapp.model.MovieReview
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieReviewResponse {
    @Json(name = "id")
    var id: Long = 0

    @Json(name = "page")
    var page: Int = 0

    @Json(name = "results")
    var results: List<MovieReview> = listOf()

    @Json(name = "total_pages")
    var total_pages: Int = 0

    @Json(name = "total_results")
    var total_results: Int = 0
}