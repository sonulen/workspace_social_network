package com.redmadrobot.app.di.auth.authRepository

import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkProvider::class, SessionRepositoryProvider::class],
    modules = [AuthRepositoryModule::class]
)
interface AuthRepositoryComponent : AuthRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(
                networkProvider: NetworkProvider,
                sessionRepositoryProvider: SessionRepositoryProvider,
            ): AuthRepositoryProvider {
                return DaggerAuthRepositoryComponent.builder()
                    .networkProvider(networkProvider)
                    .sessionRepositoryProvider(sessionRepositoryProvider)
                    .build()
            }
        }
    }
}
