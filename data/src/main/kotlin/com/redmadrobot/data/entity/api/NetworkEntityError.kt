package com.redmadrobot.data.entity.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkEntityError(
    @Json(name = "code") val code: String,
    @Json(name = "message") val message: String,
)
