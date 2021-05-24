package com.redmadrobot.app.di.auth.login

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signin.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        SessionRepositoryProvider::class,
        NetworkProvider::class,
        MapMemoryProvider::class,
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
            networkProvider: NetworkProvider,
            mapMemoryProvider: MapMemoryProvider,
        ): LoginComponent
    }

    companion object {
        fun init(appProvider: AppProvider): LoginComponent {
            return DaggerLoginComponent.factory()
                .create(appProvider, appProvider, appProvider)
        }
    }
}
