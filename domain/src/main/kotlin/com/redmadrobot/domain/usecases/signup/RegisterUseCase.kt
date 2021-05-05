package com.redmadrobot.domain.usecases.signup

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidator
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: AuthValidator,
) {
    suspend fun register(email: String, password: String): Result<*> {
        return authRepository.register(email, password)
    }

    fun isEmailValid(email: String): Boolean = validator.isEmailValid(email)
    fun isPasswordValid(password: String): Boolean = validator.isPasswordValid(password)
}
