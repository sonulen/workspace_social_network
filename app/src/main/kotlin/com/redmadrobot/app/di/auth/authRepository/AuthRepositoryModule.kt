package com.redmadrobot.app.di.auth.authRepository

import com.redmadrobot.app.di.qualifiers.Mock
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.data.repository.AuthRepositoryMockImpl
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthRepositoryModule {
    @Provides
    @Mock
    @Singleton
    fun provideAuthRepositoryMock(): AuthRepository = AuthRepositoryMockImpl()

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        api: AuthApi,
        session: SessionRepository,
    ): AuthRepository = AuthRepositoryImpl(api, session)
}
