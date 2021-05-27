package com.redmadrobot.data.repository

import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.util.toUserProfileData
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.UserDataRepository
import com.redmadrobot.mapmemory.MapMemory
import com.redmadrobot.mapmemory.shared
import com.redmadrobot.mapmemory.sharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val api: WorkspaceApi,
    private val memory: MapMemory,
) : UserDataRepository {
    private val userProfileData: MutableSharedFlow<UserProfileData> by memory.sharedFlow<UserProfileData>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).shared("USER_PROFILE_DATA")

    suspend fun initialize() {
        val cachedUserData: UserProfileData? by memory.shared("USER_PROFILE_DATA")
        cachedUserData?.let {
            userProfileData.tryEmit(it)
        }
        if (cachedUserData == null) {
            val networkEntityUserProfile = api.meGetProfile()
            userProfileData.tryEmit(networkEntityUserProfile.toUserProfileData())
        }
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

        userProfileData.emit(
            networkEntityUserProfile.toUserProfileData()
        )
        emit(Unit)
    }

    override suspend fun getUserProfileDataFlow(): SharedFlow<UserProfileData> {
        initialize()
        return userProfileData.asSharedFlow()
    }
}
