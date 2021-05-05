package com.redmadrobot.app.di.auth.register

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.authClient.AuthClientProvider
import com.redmadrobot.app.ui.auth.signup.register.RegisterFragment
import dagger.Component

@Component(
    dependencies = [
        AuthClientProvider::class,
    ],
    modules = [
        RegisterViewModelModule::class,
        AuthValidatorModule::class
    ]
)
interface RegisterComponent {
    fun inject(obj: RegisterFragment)

    @Component.Factory
    interface Factory {
        fun create(
            authClient: AuthClientProvider,
        ): RegisterComponent
    }

    companion object {
        fun init(appProvider: AppProvider): RegisterComponent {
            return DaggerRegisterComponent.factory()
                .create(appProvider)
        }
    }
}
