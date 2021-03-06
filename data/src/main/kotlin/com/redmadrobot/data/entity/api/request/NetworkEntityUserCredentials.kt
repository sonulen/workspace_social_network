package com.redmadrobot.data.entity.api.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkEntityUserCredentials(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
)
