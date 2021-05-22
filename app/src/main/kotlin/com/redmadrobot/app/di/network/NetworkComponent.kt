package com.redmadrobot.app.di.network

import com.redmadrobot.app.di.android.AndroidToolsProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AndroidToolsProvider::class],
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider {
    class Builder private constructor() {
        companion object {
            fun build(androidToolsProvider: AndroidToolsProvider): NetworkProvider {
                return DaggerNetworkComponent.builder()
                    .androidToolsProvider(androidToolsProvider)
                    .build()
            }
        }
    }
}
