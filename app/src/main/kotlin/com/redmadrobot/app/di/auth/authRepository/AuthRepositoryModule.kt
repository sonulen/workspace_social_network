package com.redmadrobot.app.di.auth.authRepository

import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides

@Module
object AuthRepositoryModule {
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
}
