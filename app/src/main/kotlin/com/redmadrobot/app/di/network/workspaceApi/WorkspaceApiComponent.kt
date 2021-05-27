package com.redmadrobot.app.di.network.workspaceApi

import com.redmadrobot.app.di.android.AndroidToolsProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        AndroidToolsProvider::class,
        SessionRepositoryProvider::class,
        NetworkProvider::class,
        MapMemoryProvider::class
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        WorkspaceApiModule::class
    ]
)
interface WorkspaceApiComponent : WorkspaceApiProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                androidToolsProvider: AndroidToolsProvider,
                sessionRepositoryProvider: SessionRepositoryProvider,
                networkProvider: NetworkProvider,
                mapMemoryProvider: MapMemoryProvider,
            ): WorkspaceApiProvider {
                return DaggerWorkspaceApiComponent.builder()
                    .androidToolsProvider(androidToolsProvider)
                    .sessionRepositoryProvider(sessionRepositoryProvider)
                    .networkProvider(networkProvider)
                    .mapMemoryProvider(mapMemoryProvider)
                    .build()
            }
        }
    }
}
