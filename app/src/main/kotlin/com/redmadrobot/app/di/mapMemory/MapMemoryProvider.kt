package com.redmadrobot.app.di.mapMemory

import com.redmadrobot.mapmemory.MapMemory

interface MapMemoryProvider {
    fun provideMapMemory(): MapMemory
}
