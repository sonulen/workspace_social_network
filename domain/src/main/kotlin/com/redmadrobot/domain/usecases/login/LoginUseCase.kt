package com.redmadrobot.domain.usecases.login

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) {
    suspend fun login(email: String, password: String) {
        val tokens = authRepository.login(email, password)
        sessionRepository.saveSession(tokens)
    }
}
