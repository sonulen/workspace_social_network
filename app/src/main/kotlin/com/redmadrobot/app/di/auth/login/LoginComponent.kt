package com.redmadrobot.app.di.auth.login

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signin.LoginFragment
import dagger.Component

@Component(
    dependencies = [
        SessionRepositoryProvider::class,
    ],
    modules = [
        LoginViewModelModule::class,
        AuthValidatorModule::class,
        AuthRepositoryModule::class
    ]
)
interface LoginComponent {
    fun inject(obj: LoginFragment)

    @Component.Factory
    interface Factory {
        fun create(
            sessionRepository: SessionRepositoryProvider,
        ): LoginComponent
    }

    companion object {
        fun init(appProvider: AppProvider): LoginComponent {
            return DaggerLoginComponent.factory()
                .create(appProvider)
        }
    }
}
