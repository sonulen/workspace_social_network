package com.redmadrobot.app.di.workspace.profile

import com.redmadrobot.app.ui.workspace.profile.ProfileFragment
import dagger.Component

@Component(
    modules = [
        ProfileViewModelModule::class,
    ]
)
interface ProfileComponent {
    fun inject(obj: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

    companion object {
        fun init(): ProfileComponent {
            return DaggerProfileComponent.factory().create()
        }
    }
}
