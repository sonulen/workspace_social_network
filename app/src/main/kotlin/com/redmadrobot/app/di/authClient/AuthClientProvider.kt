package com.redmadrobot.app.di.authClient

import com.redmadrobot.domain.repository.AuthRepository

interface AuthClientProvider {
    fun authClient(): AuthRepository
}
