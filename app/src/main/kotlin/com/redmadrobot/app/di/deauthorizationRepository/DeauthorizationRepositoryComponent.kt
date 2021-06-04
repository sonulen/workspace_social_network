package com.redmadrobot.app.di.deauthorizationRepository

import com.redmadrobot.app.di.memoryCache.MemoryCacheProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        SessionRepositoryProvider::class,
        MemoryCacheProvider::class
    ],
    modules = [DeauthorizationRepositoryModule::class]
)
interface DeauthorizationRepositoryComponent : DeauthorizationRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                sessionRepositoryProvider: SessionRepositoryProvider,
                memoryCacheProvider: MemoryCacheProvider,
            ): DeauthorizationRepositoryProvider {
                return DaggerDeauthorizationRepositoryComponent.builder()
                    .sessionRepositoryProvider(sessionRepositoryProvider)
                    .memoryCacheProvider(memoryCacheProvider)
                    .build()
            }
        }
    }
}
