package com.redmadrobot.data.repository

import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.util.toUserProfileData
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.UserDataRepository
import com.redmadrobot.mapmemory.MapMemory
import com.redmadrobot.mapmemory.stateFlow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val api: WorkspaceApi,
    private val memory: MapMemory,
) : UserDataRepository {
    private val userProfileData: MutableStateFlow<UserProfileData> by memory.stateFlow(UserProfileData())

    suspend fun initialize() {
        val networkEntityUserProfile = api.meGetProfile()
        userProfileData.emit(networkEntityUserProfile.toUserProfileData())
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

    override suspend fun getUserProfileDataFlow(): StateFlow<UserProfileData> {
        initialize()
        return userProfileData.asStateFlow()
    }
}
