package com.redmadrobot.app.di.auth.welcome

import com.redmadrobot.app.ui.auth.welcome.WelcomeFragment
import dagger.Component

@Component(
    modules = [
        WelcomeViewModelModule::class,
    ]
)
interface WelcomeComponent {
    fun inject(obj: WelcomeFragment)

    @Component.Factory
    interface Factory {
        fun create(): WelcomeComponent
    }

    companion object {
        fun init(): WelcomeComponent {
            return DaggerWelcomeComponent.factory().create()
        }
    }
}
