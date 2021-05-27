package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.repository.UserDataRepositoryImpl
import com.redmadrobot.domain.repository.UserDataRepository
import com.redmadrobot.mapmemory.MapMemory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserDataRepositoryModule {
    @Provides
    @Reusable
    fun provideUserDataRepositoryImpl(
        api: WorkspaceApi,
        cache: MapMemory,
    ): UserDataRepository = UserDataRepositoryImpl(api, cache)
}
