package com.redmadrobot.data.entity.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkEntityRefreshToken(
    @Json(name = "refresh_token") val refreshToken: String,
)
