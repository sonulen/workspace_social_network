package com.redmadrobot.app.di.auth.login

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signin.LoginFragment
import dagger.Component

@Component(
    dependencies = [
        AppProvider::class,
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        AuthValidatorModule::class,
        LoginViewModelModule::class,
    ]
)
interface LoginComponent {
    fun inject(obj: LoginFragment)

    @Component.Factory
    interface Factory {
        fun create(appProvider: AppProvider): LoginComponent
    }

    companion object {
        fun init(appProvider: AppProvider): LoginComponent {
            return DaggerLoginComponent.factory()
                .create(appProvider)
        }
    }
}
