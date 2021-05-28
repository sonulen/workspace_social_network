package com.redmadrobot.app.di.mapMemory

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MapMemoryModule::class])
interface MapMemoryComponent : MapMemoryProvider {
    companion object {
        fun init(): MapMemoryComponent {
            return DaggerMapMemoryComponent.builder()
                .build()
        }
    }
}
