package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryMockImpl : AuthRepository {
    /**
     * /see [AuthRepository.logout]
     */
    override suspend fun logout(): Flow<Unit> = flow {
        delay(timeMillis = 5_000)
        emit(Unit)
    }

    /**
     * /see [AuthRepository.login]
     */
    override suspend fun login(email: String, password: String): Tokens {
        delay(timeMillis = 5_000)
        return Tokens("access", "refresh")
    }

    /**
     * /see [AuthRepository.register]
     */
    override suspend fun register(email: String, password: String): Tokens {
        delay(timeMillis = 5_000)
        return Tokens("access", "refresh")
    }

    /**
     * /see [AuthRepository.updateProfile]
     */
    override suspend fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String?,
    ): UserProfileData {
        delay(timeMillis = 5000)
        return UserProfileData(
            id = "id",
            firstName = "first name",
            lastName = "last name",
            nickname = "nickname",
            avatarUrl = "avatar url",
            birthDay = "2021-12-21",
        )
    }
}
