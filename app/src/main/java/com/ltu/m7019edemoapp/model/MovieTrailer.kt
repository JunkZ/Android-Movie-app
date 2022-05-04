package com.ltu.m7019edemoapp.model

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.moshi.Json

data class MovieTrailer (
    @Json(name = "id")
    var trailer_id: String,

    @Json(name = "name")
    var name: String,

    @Json(name = "key")
    var key: String,

    @Json(name = "site")
    var site: String
)