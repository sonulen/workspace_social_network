package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Post
import com.redmadrobot.domain.entity.repository.UserProfileData
import kotlinx.coroutines.flow.Flow

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

    fun getUserProfileDataFlow(): Flow<UserProfileData>
    fun getUserFeed(): Flow<List<Post>>
    fun changeLikePost(postId: String, isLike: Boolean): Flow<Unit>
}
