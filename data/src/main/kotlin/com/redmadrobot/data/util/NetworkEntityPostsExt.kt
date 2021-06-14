package com.redmadrobot.data.util

import android.location.Geocoder
import com.redmadrobot.data.entity.api.response.NetworkEntityPosts
import com.redmadrobot.domain.entity.repository.Feed

fun NetworkEntityPosts.toFeed(geocoder: Geocoder): Feed = map { it.toPost(geocoder) }
