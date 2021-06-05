package com.redmadrobot.data.util

import com.redmadrobot.data.entity.api.response.NetworkEntityPosts
import com.redmadrobot.domain.entity.repository.Feed

fun NetworkEntityPosts.toFeed(): Feed = map { it.toPost() }

