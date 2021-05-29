package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.repository.UserDataRepositoryImpl
import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserDataRepositoryModule {
    @Provides
    @Reusable
    fun provideUserDataRepositoryImpl(
        api: WorkspaceApi,
        userProfileDataStorage: UserProfileDataStorage,
    ): UserDataRepository = UserDataRepositoryImpl(api, userProfileDataStorage)
}
