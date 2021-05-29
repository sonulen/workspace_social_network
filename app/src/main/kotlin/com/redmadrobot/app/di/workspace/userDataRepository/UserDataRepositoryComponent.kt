package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryProvider
import com.redmadrobot.app.di.memoryCache.MemoryCacheProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.network.workspaceApi.WorkspaceApiModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component

@Component(
    dependencies = [
        DeauthorizationRepositoryProvider::class,
        MemoryCacheProvider::class,
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
                memoryCacheProvider: MemoryCacheProvider,
                networkProvider: NetworkProvider,
                sessionRepositoryProvider: SessionRepositoryProvider,
                deauthorizationRepositoryProvider: DeauthorizationRepositoryProvider,
            ): UserDataRepositoryProvider {
                return DaggerUserDataRepositoryComponent.builder()
                    .memoryCacheProvider(memoryCacheProvider)
                    .networkProvider(networkProvider)
                    .sessionRepositoryProvider(sessionRepositoryProvider)
                    .deauthorizationRepositoryProvider(deauthorizationRepositoryProvider)
                    .build()
            }
        }
    }
}
