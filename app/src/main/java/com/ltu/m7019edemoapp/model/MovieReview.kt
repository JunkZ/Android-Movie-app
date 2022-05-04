package com.ltu.m7019edemoapp.model

import com.squareup.moshi.Json

data class MovieReview (
    @Json(name = "id")
    var review_id: String,

    @Json(name = "author")
    var author: String,

    @Json(name = "content")
    var content: String
)