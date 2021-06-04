package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.repository.UserDataRepositoryImpl
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
}
