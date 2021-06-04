package com.redmadrobot.app.di.deauthorizationRepository

import com.redmadrobot.domain.repository.DeauthorizationRepository

interface DeauthorizationRepositoryProvider {
    fun provideDeauthorizationRepository(): DeauthorizationRepository
}
