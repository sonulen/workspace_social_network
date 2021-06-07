package com.redmadrobot.domain.entity.repository

data class Post(
    val author: UserProfileData,
    val id: String,
    val imageUrl: String? = null,
    val lat: Double,
    var likes: Int,
    var liked: Boolean,
    val lon: Double,
    val text: String,
)
