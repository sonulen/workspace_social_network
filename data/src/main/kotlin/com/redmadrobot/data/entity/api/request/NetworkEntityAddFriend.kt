package com.redmadrobot.data.entity.api.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkEntityAddFriend(
    @Json(name = "user_id") val userId: String,
)
