package com.redmadrobot.data.repository

import com.redmadrobot.data.entity.api.request.NetworkEntityUserCredentials
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.mapmemory.MapMemory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val session: SessionRepository,
    private val memory: MapMemory,
) : AuthRepository {
    /**
     * /see [AuthRepository.logout]
     */
    override suspend fun logout(): Flow<Unit> = flow {
        api.logout(
            "Bearer " + (
                session.getAccessToken() ?: throw IllegalArgumentException("Access token required")
                )
        )
        session.clear()
        memory.clear()
        emit(Unit)
    }

    /**
     * /see [AuthRepository.login]
     */
    override suspend fun login(email: String, password: String): Tokens {
        val tokens = api.login(NetworkEntityUserCredentials(email, password))
        return Tokens(tokens.accessToken, tokens.refreshToken)
    }

    /**
     * /see [AuthRepository.register]
     */
    override suspend fun register(email: String, password: String): Tokens {
        val tokens = api.registration(NetworkEntityUserCredentials(email, password))
        return Tokens(tokens.accessToken, tokens.refreshToken)
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
        val userProfileData = api.mePatchProfile(
            accessToken = "Bearer " + (
                session.getAccessToken() ?: throw IllegalArgumentException("Access token required")
                ),
            nickname = nickname,
            firstName = firstName,
            lastName = lastName,
            birthday = birthDay,
        )

        return UserProfileData(
            id = userProfileData.id,
            firstName = userProfileData.firstName,
            lastName = userProfileData.lastName,
            nickname = userProfileData.nickname,
            avatarUrl = userProfileData.avatarUrl ?: "",
            birthDay = userProfileData.birthDay,
        )
    }
}
