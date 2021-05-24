package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.UserDataRepository
import com.redmadrobot.mapmemory.MapMemory
import com.redmadrobot.mapmemory.stateFlow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val memory: MapMemory,
) : UserDataRepository {
    private val userProfileData: MutableStateFlow<UserProfileData> by memory.stateFlow(
        UserProfileData(
            id = "id",
            firstName = "andrey",
            lastName = "tolmachev",
            nickname = "sonulen",
            avatarUrl = null,
            birthDay = "1993-07-29"
        )
    )

    override fun updateUserProfileData(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String?,
    ): Flow<Unit> = flow {
        // TODO Это должен быть поход в сеть на скачивание /me
        userProfileData.emit(
            userProfileData.value.copy(
                firstName = firstName,
                lastName = lastName,
                nickname = nickname,
                avatarUrl = avatarUrl,
                birthDay = birthDay
            )
        )
        emit(Unit)
    }

    override fun getUserProfileDataFlow(): StateFlow<UserProfileData> {
        // TODO Поход в сеть на patch /me
        return userProfileData.asStateFlow()
    }
}
