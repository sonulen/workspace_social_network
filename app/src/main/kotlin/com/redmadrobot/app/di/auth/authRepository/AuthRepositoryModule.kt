package com.redmadrobot.app.di.auth.authRepository

import com.redmadrobot.app.di.qualifiers.Mock
import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.data.repository.AuthRepositoryMockImpl
import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.mapmemory.MapMemory
import dagger.Module
import dagger.Provides

@Module
object AuthRepositoryModule {
    @Provides
    @Mock
    fun provideAuthRepositoryMock(): AuthRepository = AuthRepositoryMockImpl()

    @Provides
    fun provideAuthRepositoryImpl(
        api: AuthApi,
        session: SessionRepository,
        mapMemory: MapMemory,
        userProfileDataStorage: UserProfileDataStorage,
    ): AuthRepository = AuthRepositoryImpl(
        api = api,
        session = session,
        memory = mapMemory,
        userProfileDataStorage = userProfileDataStorage,
    )
}
