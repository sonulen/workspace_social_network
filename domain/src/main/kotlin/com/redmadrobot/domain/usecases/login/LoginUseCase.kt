package com.redmadrobot.domain.usecases.login

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) {
    fun login(email: String, password: String): Flow<Unit> = flow {
        val tokens = authRepository.login(email, password)
        sessionRepository.saveSession(tokens)
        emit(Unit)
    }
}
