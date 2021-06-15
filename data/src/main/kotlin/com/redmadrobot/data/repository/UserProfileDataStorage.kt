package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Post
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.mapmemory.MapMemory
import com.redmadrobot.mapmemory.sharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class UserProfileDataStorage @Inject constructor(
    private val memory: MapMemory,
) {
    private val _userProfileData by memory.sharedFlow<UserProfileData>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val userProfileData = _userProfileData.asSharedFlow()
    var isProfileEmpty = true
        private set

    private val _userFeed by memory.sharedFlow<List<Post>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val userFeed = _userFeed.asSharedFlow()
    var isFeedEmpty = true
        private set

    suspend fun updateUserProfileData(userProfileData: UserProfileData) {
        isProfileEmpty = false
        _userProfileData.emit(userProfileData)
    }

    suspend fun updateFeed(posts: List<Post>) {
        isFeedEmpty = posts.isEmpty()
        _userFeed.emit(posts)
    }

    fun clear() {
        isFeedEmpty = true
        isProfileEmpty = true
    }
}
