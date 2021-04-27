package com.redmadrobot.data.entity.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(
    @field:Json(name = "code") val code: String,
    @field:Json(name = "message") val message: String,
)
