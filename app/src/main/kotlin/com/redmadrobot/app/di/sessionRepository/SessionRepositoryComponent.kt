package com.redmadrobot.app.di.sessionRepository

import com.redmadrobot.app.di.android.AndroidToolsProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AndroidToolsProvider::class],
    modules = [SessionRepositoryModule::class]
)
interface SessionRepositoryComponent : SessionRepositoryProvider {
    class Builder private constructor() {
        companion object {
            fun build(androidToolsProvider: AndroidToolsProvider): SessionRepositoryProvider {
                return DaggerSessionRepositoryComponent.builder()
                    .androidToolsProvider(androidToolsProvider)
                    .build()
            }
        }
    }
}
