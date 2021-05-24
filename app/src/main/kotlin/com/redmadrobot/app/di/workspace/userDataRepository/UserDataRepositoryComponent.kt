package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkProvider::class, SessionRepositoryProvider::class, MapMemoryProvider::class],
    modules = [UserDataRepositoryModule::class]
)
interface UserDataRepositoryComponent : UserDataRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                mapMemoryProvider: MapMemoryProvider,
            ): UserDataRepositoryProvider {
                return DaggerUserDataRepositoryComponent.builder()
                    .mapMemoryProvider(mapMemoryProvider)
                    .build()
            }
        }
    }
}
