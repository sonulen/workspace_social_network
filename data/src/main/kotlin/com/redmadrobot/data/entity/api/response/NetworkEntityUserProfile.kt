package com.redmadrobot.data.entity.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkEntityUserProfile(
    @Json(name = "id") val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "avatar_url") val avatarUrl: String? = null,
    @Json(name = "birth_day") val birthDay: String,
)
