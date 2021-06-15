package com.redmadrobot.data.util

import android.location.Geocoder
import com.redmadrobot.data.entity.api.response.NetworkEntityPosts
import com.redmadrobot.domain.entity.repository.Post

fun NetworkEntityPosts.toPostList(geocoder: Geocoder): List<Post> = map { it.toPost(geocoder) }
