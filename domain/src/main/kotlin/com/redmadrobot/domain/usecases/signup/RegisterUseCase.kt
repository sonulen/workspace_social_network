package com.redmadrobot.domain.usecases.signup

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) {
    fun register(email: String, password: String): Flow<Boolean> = flow {
        val tokens = authRepository.register(email, password)
        sessionRepository.saveSession(tokens)
        emit(true)
    }

    fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
    ): Flow<Boolean> = flow {
        // TODO положить в mapmemmory
        authRepository.updateProfile(
            nickname = nickname,
            firstName = firstName,
            lastName = lastName,
            birthDay = birthDay
        )

        emit(true)
    }
}
