package com.redmadrobot.data.util

import android.location.Address
import android.location.Geocoder
import com.redmadrobot.data.entity.api.response.NetworkEntityPostItem
import com.redmadrobot.domain.entity.repository.Post

fun NetworkEntityPostItem.toPost(geocoder: Geocoder): Post {
    val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
    var location = "Там далеко-далеко"
    if (addresses.isNotEmpty() && !addresses[0].locality.isNullOrEmpty()) {
        location = addresses[0].locality
    }

    return Post(
        author = author.toUserProfileData(),
        id = id,
        imageUrl = imageUrl,
        lat = lat,
        likes = likes,
        liked = liked,
        lon = lon,
        location = location,
        text = text,
    )
}
