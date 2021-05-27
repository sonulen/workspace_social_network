package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkProvider::class, MapMemoryProvider::class],
    modules = [UserDataRepositoryModule::class]
)
interface UserDataRepositoryComponent : UserDataRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                mapMemoryProvider: MapMemoryProvider,
                networkProvider: NetworkProvider,
            ): UserDataRepositoryProvider {
                return DaggerUserDataRepositoryComponent.builder()
                    .mapMemoryProvider(mapMemoryProvider)
                    .networkProvider(networkProvider)
                    .build()
            }
        }
    }
}
