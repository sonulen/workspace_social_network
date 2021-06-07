package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Feed
import com.redmadrobot.domain.entity.repository.UserProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface UserDataRepository {
    fun initProfileData(): Flow<Unit>
    fun initFeed(): Flow<Unit>
    fun updateFeed(): Flow<Unit>

    fun updateUserProfileData(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String? = null,
    ): Flow<Unit>

    fun getUserProfileDataFlow(): SharedFlow<UserProfileData>
    fun getUserFeed(): SharedFlow<Feed>
    fun changeLikePost(postId: String, isLike: Boolean): Flow<Unit>
}
