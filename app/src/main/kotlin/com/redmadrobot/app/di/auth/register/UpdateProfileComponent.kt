package com.redmadrobot.app.di.auth.register

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signup.updateProfile.UpdateProfileFragment
import dagger.Component

@Component(
    dependencies = [
        SessionRepositoryProvider::class,
    ],
    modules = [
        UpdateProfileViewModelModule::class,
        AuthValidatorModule::class,
        AuthRepositoryModule::class
    ]
)
interface UpdateProfileComponent {
    fun inject(obj: UpdateProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            sessionRepository: SessionRepositoryProvider,
        ): UpdateProfileComponent
    }

    companion object {
        fun init(appProvider: AppProvider): UpdateProfileComponent {
            return DaggerUpdateProfileComponent.factory()
                .create(appProvider)
        }
    }
}
