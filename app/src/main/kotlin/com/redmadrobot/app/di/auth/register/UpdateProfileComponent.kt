package com.redmadrobot.app.di.auth.register

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.authClient.AuthClientProvider
import com.redmadrobot.app.ui.auth.signup.updateProfile.UpdateProfileFragment
import dagger.Component

@Component(
    dependencies = [
        AuthClientProvider::class,
    ],
    modules = [
        UpdateProfileViewModelModule::class,
        AuthValidatorModule::class
    ]
)
interface UpdateProfileComponent {
    fun inject(obj: UpdateProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            authClient: AuthClientProvider,
        ): UpdateProfileComponent
    }

    companion object {
        fun init(appProvider: AppProvider): UpdateProfileComponent {
            return DaggerUpdateProfileComponent.factory()
                .create(appProvider)
        }
    }
}
