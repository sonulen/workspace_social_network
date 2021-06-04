package com.redmadrobot.data.repository

import com.redmadrobot.data.entity.api.request.NetworkEntityRefreshToken
import com.redmadrobot.data.entity.api.request.NetworkEntityUserCredentials
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.util.toTokens
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
    private val userProfileDataStorage: UserProfileDataStorage,
) : AuthRepository {
    /**
     * /see [AuthRepository.logout]
     */
    override fun logout(): Flow<Unit> = flow {
        try {
            api.logout(
                AuthRepository.HEADER_TOKEN_PREFIX +
                    requireNotNull(session.getAccessToken()) { "Access token required" }
            )
        } finally {
            session.clear()
            memory.clear()
            userProfileDataStorage.clear()
        }
        emit(Unit)
    }

    /**
     * /see [AuthRepository.login]
     */
    override fun login(email: String, password: String): Flow<Tokens> = flow {
        val tokens = api.login(NetworkEntityUserCredentials(email, password))
        session.saveSession(tokens.toTokens())
        emit(tokens.toTokens())
    }

    /**
     * /see [AuthRepository.refresh]
     */
    override suspend fun refresh(): Tokens {
        val refreshToken = requireNotNull(session.getRefreshToken()) { "Refresh token required" }
        val tokens = api.refresh(NetworkEntityRefreshToken(refreshToken))
        session.saveSession(tokens.toTokens())
        return tokens.toTokens()
    }

    /**
     * /see [AuthRepository.register]
     */
    override fun register(email: String, password: String): Flow<Tokens> = flow {
        val tokens = api.registration(NetworkEntityUserCredentials(email, password))
        session.saveSession(tokens.toTokens())
        emit(tokens.toTokens())
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
        val networkEntityUserProfile = api.mePatchProfile(
            accessToken = AuthRepository.HEADER_TOKEN_PREFIX +
                requireNotNull(session.getAccessToken()) { "Access token required" },
            nickname = nickname,
            firstName = firstName,
            lastName = lastName,
            birthday = birthDay,
        )

        userProfileDataStorage.updateUserProfileData(networkEntityUserProfile.toUserProfileData())
        emit(networkEntityUserProfile.toUserProfileData())
    }
}
