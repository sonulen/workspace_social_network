package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    /**
     * /see [AuthRepository.logout]
     */
    override suspend fun logout() {
        delay(timeMillis = 5_000)
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
    ): Boolean {
        delay(timeMillis = 5000)
        return true
    }
}
