package com.redmadrobot.app.di.mapMemory

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MapMemoryModule::class])
interface MapMemoryComponent : MapMemoryProvider {
    @Component.Builder
    interface ComponentBuilder {
        fun build(): MapMemoryComponent
    }

    class Builder private constructor() {

        companion object {
            fun build(): MapMemoryProvider {
                return DaggerMapMemoryComponent.builder()
                    .build()
            }
        }
    }
}
