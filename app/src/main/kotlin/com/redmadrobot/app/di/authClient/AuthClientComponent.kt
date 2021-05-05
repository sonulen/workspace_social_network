package com.redmadrobot.app.di.authClient

import com.redmadrobot.app.di.android.AndroidToolsProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AndroidToolsProvider::class],
    modules = [AuthClientModule::class]
)
interface AuthClientComponent : AuthClientProvider {
    class Builder private constructor() {
        companion object {
            fun build(androidToolsProvider: AndroidToolsProvider): AuthClientProvider {
                return DaggerAuthClientComponent.builder()
                    .androidToolsProvider(androidToolsProvider)
                    .build()
            }
        }
    }
}
