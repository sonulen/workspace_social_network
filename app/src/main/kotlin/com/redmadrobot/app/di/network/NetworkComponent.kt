package com.redmadrobot.app.di.network

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider {
    class Builder private constructor() {
        companion object {
            fun build(): NetworkProvider {
                return DaggerNetworkComponent.builder()
                    .build()
            }
        }
    }
}
