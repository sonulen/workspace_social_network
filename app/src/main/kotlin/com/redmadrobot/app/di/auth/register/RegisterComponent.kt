package com.redmadrobot.app.di.auth.register

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signup.register.RegisterFragment
import dagger.Component

@Component(
    dependencies = [
        SessionRepositoryProvider::class,
    ],
    modules = [
        RegisterViewModelModule::class,
        AuthValidatorModule::class,
        AuthRepositoryModule::class
    ]
)
interface RegisterComponent {
    fun inject(obj: RegisterFragment)

    @Component.Factory
    interface Factory {
        fun create(
            sessionRepository: SessionRepositoryProvider,
        ): RegisterComponent
    }

    companion object {
        fun init(appProvider: AppProvider): RegisterComponent {
            return DaggerRegisterComponent.factory()
                .create(appProvider)
        }
    }
}
