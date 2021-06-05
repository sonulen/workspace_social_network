package com.redmadrobot.data.entity.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkEntityPostItem(
    @Json(name = "author") val author: NetworkEntityUserProfile,
    @Json(name = "id") val id: String,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "lat") val lat: Double,
    @Json(name = "likes") val likes: Int,
    @Json(name = "liked") val liked: Boolean,
    @Json(name = "lon") val lon: Double,
    @Json(name = "text") val text: String,
)
