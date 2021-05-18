package com.redmadrobot.domain.usecases.signup

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) {
    suspend fun register(email: String, password: String): Boolean {
        val tokens = authRepository.register(email, password)
        sessionRepository.saveSession(tokens)
        return true
    }

    suspend fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
    ): Boolean = authRepository.updateProfile(
        nickname = nickname,
        firstName = firstName,
        lastName = lastName,
        birthDay = birthDay
    )
}
