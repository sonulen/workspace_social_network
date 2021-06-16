package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Post
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.mapmemory.MapMemory
import com.redmadrobot.mapmemory.stateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserProfileDataStorage @Inject constructor(
    private val memory: MapMemory,
) {
    private val _userProfileData: MutableStateFlow<UserProfileData?> by memory.stateFlow(null)

    val userProfileData = _userProfileData.asSharedFlow().filterNotNull()
    var isProfileEmpty = true
        private set

    private val _userFeed: MutableStateFlow<List<Post>?> by memory.stateFlow(null)
    val userFeed = _userFeed.asSharedFlow().filterNotNull()

    suspend fun updateUserProfileData(userProfileData: UserProfileData) {
        _userProfileData.emit(userProfileData)
    }

    suspend fun updateFeed(posts: List<Post>) {
        _userFeed.emit(posts)
    }

    suspend fun clear() {
        _userProfileData.emit(null)
        _userFeed.emit(null)
    }

    suspend fun changeLikePost(postId: String, like: Boolean) {
        val feed: MutableList<Post>? = _userFeed.first()?.toMutableList()
        if (feed != null) {
            val post: Post? = feed.find { post -> post.id == postId }

            if (post != null) {
                val postIndex = feed.indexOf(post)
                feed[postIndex] = post.copy(
                    liked = like,
                    likes = post.likes + if (like) 1 else -1
                )
            }
        }
        _userFeed.emit(feed)
    }
}
