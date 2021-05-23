package com.redmadrobot.app.di.mapMemory

import com.redmadrobot.mapmemory.MapMemory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MapMemoryModule {
    @Provides
    @Singleton
    fun provideMapMemory(): MapMemory = MapMemory()
}
