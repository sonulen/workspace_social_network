package com.redmadrobot.data.repository

import com.redmadrobot.data.entity.api.request.NetworkEntityRefreshToken
import com.redmadrobot.data.entity.api.request.NetworkEntityUserCredentials
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.util.toUserProfileData
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
    override fun logout(): Flow<Unit> = flow {
        try {
            api.logout("Bearer " + requireNotNull(session.getAccessToken()) { "Access token required" })
        } finally {
            session.clear()
            memory.clear()
        }
        emit(Unit)
    }

    /**
     * /see [AuthRepository.login]
     */
    override fun login(email: String, password: String): Flow<Tokens> = flow {
        val tokens = api.login(NetworkEntityUserCredentials(email, password))
        session.saveSession(tokens.toUserProfileData())
        emit(tokens.toUserProfileData())
    }

    /**
     * /see [AuthRepository.refresh]
     */
    override fun refresh(): Flow<Tokens> = flow {
        val refreshToken = requireNotNull(session.getRefreshToken()) { "Refresh token required" }
        val tokens = api.refresh(NetworkEntityRefreshToken(refreshToken))
        session.saveSession(tokens.toUserProfileData())
        emit(tokens.toUserProfileData())
    }

    /**
     * /see [AuthRepository.register]
     */
    override fun register(email: String, password: String): Flow<Tokens> = flow {
        val tokens = api.registration(NetworkEntityUserCredentials(email, password))
        session.saveSession(tokens.toUserProfileData())
        emit(tokens.toUserProfileData())
    }

    /**
     * /see [AuthRepository.updateProfile]
     */
    override fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
    ): Flow<UserProfileData> = flow {
        val userProfileData = api.mePatchProfile(
            accessToken = "Bearer " + (
                session.getAccessToken() ?: throw IllegalArgumentException("Access token required")
                ),
            nickname = nickname,
            firstName = firstName,
            lastName = lastName,
            birthday = birthDay,
        )

        var cachedUserData: UserProfileData? by memory
        cachedUserData = userProfileData.toUserProfileData()

        emit(userProfileData.toUserProfileData())
    }
}
