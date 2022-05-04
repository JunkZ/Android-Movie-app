package com.ltu.m7019edemoapp.model

import com.squareup.moshi.Json

data class MovieGenre (
    @Json(name = "name")
    var name: String
)