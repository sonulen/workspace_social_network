package com.redmadrobot.app.di.auth.authRepository

import dagger.Component

@Component(
    modules = [AuthRepositoryModule::class]
)
interface AuthRepositoryComponent : AuthRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(): AuthRepositoryProvider {
                return DaggerAuthRepositoryComponent.builder()
                    .build()
            }
        }
    }
}
