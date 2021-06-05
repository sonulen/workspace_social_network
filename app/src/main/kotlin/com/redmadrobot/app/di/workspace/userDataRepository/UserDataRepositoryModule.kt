package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.app.di.qualifiers.Mock
import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.repository.UserDataRepositoryImpl
import com.redmadrobot.data.repository.UserDataRepositoryMockImpl
import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides

@Module
object UserDataRepositoryModule {
    @Provides
    fun provideUserDataRepositoryImpl(
        api: WorkspaceApi,
        userProfileDataStorage: UserProfileDataStorage,
    ): UserDataRepository = UserDataRepositoryImpl(api, userProfileDataStorage)

    @Provides
    @Mock
    fun provideUserDataRepositoryMockImpl(
        api: WorkspaceApi,
        userProfileDataStorage: UserProfileDataStorage,
    ): UserDataRepository =
        UserDataRepositoryMockImpl(api, userProfileDataStorage, UserDataRepositoryMockImpl.MODE.FULL)
}
