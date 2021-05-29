package com.redmadrobot.app.di.memoryCache

import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.mapmemory.MapMemory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MemoryCacheModule {
    @Provides
    @Singleton
    fun provideMapMemory(): MapMemory = MapMemory()

    @Provides
    @Singleton
    fun provideUserProfileDataStorage(
        mapMemory: MapMemory,
    ): UserProfileDataStorage = UserProfileDataStorage(mapMemory)
}
