package com.redmadrobot.app.di.network.authApi

import com.redmadrobot.app.di.network.NetworkProvider
import dagger.Component

@Component(
    dependencies = [NetworkProvider::class],
    modules = [AuthApiModule::class]
)
interface AuthApiComponent : AuthApiProvider {
    class Builder private constructor() {
        companion object {
            fun build(networkProvider: NetworkProvider): AuthApiProvider {
                return DaggerAuthApiComponent.builder()
                    .networkProvider(networkProvider)
                    .build()
            }
        }
    }
}
