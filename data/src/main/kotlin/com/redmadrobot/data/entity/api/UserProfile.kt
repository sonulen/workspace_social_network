package com.redmadrobot.data.entity.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    @field:Json(name = "avatar_url") val avatarUrl: String,
    @field:Json(name = "birth_day") val birthDay: String,
    @field:Json(name = "first_name") val firstName: String,
    @field:Json(name = "id") val id: String,
    @field:Json(name = "last_name") val lastName: String,
    @field:Json(name = "nickname") val nickname: String,
)
