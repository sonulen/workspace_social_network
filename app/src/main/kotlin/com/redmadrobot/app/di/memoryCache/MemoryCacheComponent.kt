package com.redmadrobot.app.di.memoryCache

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MemoryCacheModule::class])
interface MemoryCacheComponent : MemoryCacheProvider {
    companion object {
        fun init(): MemoryCacheComponent {
            return DaggerMemoryCacheComponent.builder()
                .build()
        }
    }
}
