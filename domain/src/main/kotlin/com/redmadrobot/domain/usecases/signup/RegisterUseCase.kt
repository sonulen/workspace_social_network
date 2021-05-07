package com.redmadrobot.domain.usecases.signup

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.domain.util.AuthValidator
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
    private val validator: AuthValidator,
) {
    suspend fun register(email: String, password: String): Boolean {
        val tokens = authRepository.register(email, password)
        sessionRepository.saveSession(tokens)
        return true
    }

    fun isEmailValid(email: String): Boolean = validator.isEmailValid(email)
    fun isPasswordValid(password: String): Boolean = validator.isPasswordValid(password)
}
