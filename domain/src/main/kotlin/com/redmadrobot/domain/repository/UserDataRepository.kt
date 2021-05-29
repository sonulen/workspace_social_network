package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.UserProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface UserDataRepository {
    fun updateUserProfileData(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String? = null,
    ): Flow<Unit>

    suspend fun getUserProfileDataFlow(): SharedFlow<UserProfileData>
}
