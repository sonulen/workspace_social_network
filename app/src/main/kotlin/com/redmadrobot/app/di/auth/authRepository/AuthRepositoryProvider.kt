package com.redmadrobot.app.di.auth.authRepository

import com.redmadrobot.domain.repository.AuthRepository

interface AuthRepositoryProvider {
    fun authRepository(): AuthRepository
}
