package com.redmadrobot.app.di.memoryCache

import com.redmadrobot.data.repository.UserProfileDataStorage
import com.redmadrobot.mapmemory.MapMemory

interface MemoryCacheProvider {
    fun provideMapMemory(): MapMemory
    fun provideUserDataProfileStorage(): UserProfileDataStorage
}
