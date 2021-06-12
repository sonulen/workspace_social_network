package com.redmadrobot.data.util

import com.redmadrobot.data.entity.api.response.NetworkEntityPostItem
import com.redmadrobot.domain.entity.repository.Post

fun NetworkEntityPostItem.toPost() = Post(
    author = author.toUserProfileData(),
    id = id,
    imageUrl = imageUrl,
    lat = lat,
    likes = likes,
    liked = liked,
    lon = lon,
    text = text,
)
