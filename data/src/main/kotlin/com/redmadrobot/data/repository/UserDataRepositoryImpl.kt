package com.redmadrobot.data.repository

import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.util.toFeed
import com.redmadrobot.data.util.toUserProfileData
import com.redmadrobot.domain.entity.repository.Feed
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val api: WorkspaceApi,
    private val userProfileDataStorage: UserProfileDataStorage,
) : UserDataRepository {

    override fun initProfileData(): Flow<Unit> = flow {
        if (userProfileDataStorage.isProfileEmpty) {
            requestUserProfileData()
        }
        emit(Unit)
    }

    override fun initFeed(): Flow<Unit> = flow {
        if (userProfileDataStorage.isFeedEmpty) {
            requestFeed()
        }
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

    override fun getUserProfileDataFlow(): SharedFlow<UserProfileData> = userProfileDataStorage.userProfileData
    override fun getUserFeed(): SharedFlow<Feed> = userProfileDataStorage.userFeed

    private suspend fun requestUserProfileData() {
        val networkEntityUserProfile = api.meGetProfile()
        userProfileDataStorage.updateUserProfileData(networkEntityUserProfile.toUserProfileData())
    }

    private suspend fun requestFeed() {
        val networkEntityPosts = api.feedGet()
        userProfileDataStorage.updateFeed(networkEntityPosts.toFeed())
    }
}
