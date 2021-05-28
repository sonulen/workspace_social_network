package com.redmadrobot.app.di.auth.register

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signup.updateProfile.UpdateProfileFragment
import dagger.Component

@Component(
    dependencies = [
        MapMemoryProvider::class,
        NetworkProvider::class,
        SessionRepositoryProvider::class,
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        AuthValidatorModule::class,
        UpdateProfileViewModelModule::class,
    ]
)
interface UpdateProfileComponent {
    fun inject(obj: UpdateProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            sessionRepository: SessionRepositoryProvider,
            networkProvider: NetworkProvider,
            mapMemoryProvider: MapMemoryProvider,
        ): UpdateProfileComponent
    }

    companion object {
        fun init(appProvider: AppProvider): UpdateProfileComponent {
            return DaggerUpdateProfileComponent.factory()
                .create(appProvider, appProvider, appProvider)
        }
    }
}
