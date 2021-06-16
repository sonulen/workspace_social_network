package com.redmadrobot.data.repository

import android.location.Geocoder
import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.util.toPostList
import com.redmadrobot.data.util.toUserProfileData
import com.redmadrobot.domain.entity.repository.Post
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val api: WorkspaceApi,
    private val userProfileDataStorage: UserProfileDataStorage,
    private val geocoder: Geocoder,
) : UserDataRepository {

    override fun initProfileData(): Flow<Unit> = flow {
        requestUserProfileData()
        emit(Unit)
    }

    override fun initFeed(): Flow<Unit> = flow {
        requestFeed()
        emit(Unit)
    }

    override fun updateFeed(): Flow<Unit> = flow {
        requestFeed()
        emit(Unit)
    }

    override fun updateUserProfileData(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String?,
    ): Flow<Unit> = flow {
        val networkEntityUserProfile = api.mePatchProfile(
            nickname = nickname,
            firstName = firstName,
            lastName = lastName,
            birthday = birthDay,
            avatarFile = avatarUrl.orEmpty()
        )
        userProfileDataStorage.updateUserProfileData(networkEntityUserProfile.toUserProfileData())
        emit(Unit)
    }

    override fun changeLikePost(postId: String, isLike: Boolean): Flow<Unit> = flow {
        val isSuccessful = if (isLike) {
            api.feedLike(postId).isSuccessful
        } else {
            api.feedDeleteLike(postId).isSuccessful
        }
        if (isSuccessful) {
            userProfileDataStorage.changeLikePost(postId, isLike)
        }
        emit(Unit)
    }

    private suspend fun requestUserProfileData() {
        val networkEntityUserProfile = api.meGetProfile()
        userProfileDataStorage.updateUserProfileData(networkEntityUserProfile.toUserProfileData())
    }

    private suspend fun requestFeed() {
        val networkEntityPosts = api.feedGet()
        userProfileDataStorage.updateFeed(networkEntityPosts.toPostList(geocoder))
    }

    override fun getUserProfileDataFlow(): Flow<UserProfileData> = userProfileDataStorage.userProfileData
    override fun getUserFeed(): Flow<List<Post>> = userProfileDataStorage.userFeed
}
