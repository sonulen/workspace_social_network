package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryProvider
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.network.workspaceApi.WorkspaceApiModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component

@Component(
    dependencies = [
        DeauthorizationRepositoryProvider::class,
        MapMemoryProvider::class,
        NetworkProvider::class,
        SessionRepositoryProvider::class
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        UserDataRepositoryModule::class,
        WorkspaceApiModule::class,
    ]
)
interface UserDataRepositoryComponent : UserDataRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                mapMemoryProvider: MapMemoryProvider,
                networkProvider: NetworkProvider,
                sessionRepositoryProvider: SessionRepositoryProvider,
                deauthorizationRepositoryProvider: DeauthorizationRepositoryProvider,
            ): UserDataRepositoryProvider {
                return DaggerUserDataRepositoryComponent.builder()
                    .mapMemoryProvider(mapMemoryProvider)
                    .networkProvider(networkProvider)
                    .sessionRepositoryProvider(sessionRepositoryProvider)
                    .deauthorizationRepositoryProvider(deauthorizationRepositoryProvider)
                    .build()
            }
        }
    }
}
