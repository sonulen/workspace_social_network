package com.redmadrobot.app.di.android

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidToolsModule::class])
interface AndroidToolsComponent : AndroidToolsProvider {
    @Component.Builder
    interface ComponentBuilder {

        @BindsInstance
        fun application(application: Application): ComponentBuilder

        fun build(): AndroidToolsComponent
    }

    class Builder private constructor() {

        companion object {
            fun build(application: Application): AndroidToolsProvider {
                return DaggerAndroidToolsComponent.builder()
                    .application(application)
                    .build()
            }
        }
    }
}
