package com.redmadrobot.domain.entity.repository

data class UserProfileData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nickname: String,
    val avatarUrl: String,
    val birthDay: String,
)
