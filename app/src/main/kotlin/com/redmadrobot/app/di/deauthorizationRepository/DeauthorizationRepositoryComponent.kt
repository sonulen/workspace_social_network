package com.redmadrobot.app.di.deauthorizationRepository

import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        SessionRepositoryProvider::class,
        MapMemoryProvider::class
    ],
    modules = [DeauthorizationRepositoryModule::class]
)
interface DeauthorizationRepositoryComponent : DeauthorizationRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                sessionRepositoryProvider: SessionRepositoryProvider,
                mapMemoryProvider: MapMemoryProvider,
            ): DeauthorizationRepositoryProvider {
                return DaggerDeauthorizationRepositoryComponent.builder()
                    .sessionRepositoryProvider(sessionRepositoryProvider)
                    .mapMemoryProvider(mapMemoryProvider)
                    .build()
            }
        }
    }
}
