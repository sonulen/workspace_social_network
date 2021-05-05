package com.redmadrobot.app.di.auth.login

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.authClient.AuthClientProvider
import com.redmadrobot.app.ui.auth.signin.LoginFragment
import dagger.Component

@Component(
    dependencies = [
        AuthClientProvider::class,
    ],
    modules = [
        LoginViewModelModule::class,
    ]
)
interface LoginComponent {
    fun inject(obj: LoginFragment)

    @Component.Factory
    interface Factory {
        fun create(
            authClient: AuthClientProvider,
        ): LoginComponent
    }

    companion object {
        fun init(appProvider: AppProvider): LoginComponent {
            return DaggerLoginComponent.factory()
                .create(appProvider)
        }
    }
}
