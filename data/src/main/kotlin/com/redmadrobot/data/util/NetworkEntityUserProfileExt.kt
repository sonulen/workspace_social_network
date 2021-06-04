package com.redmadrobot.data.util

import com.redmadrobot.data.entity.api.request.NetworkEntityUserProfile
import com.redmadrobot.domain.entity.repository.UserProfileData

fun NetworkEntityUserProfile.toUserProfileData() = UserProfileData(
    id = id,
    firstName = firstName,
    lastName = lastName,
    nickname = nickname,
    avatarUrl = avatarUrl,
    birthDay = birthDay,
)
