package com.redmadrobot.domain.usecases.login

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.domain.util.AuthValidator
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
    private val validator: AuthValidator,
) {
    suspend fun login(email: String, password: String): Boolean {
        val tokens = authRepository.login(email, password)
        sessionRepository.saveSession(tokens)
        return true
    }

    fun isEmailValid(email: String): Boolean = validator.isEmailValid(email)

    fun isPasswordValid(password: String): Boolean = validator.isPasswordValid(password)
}
