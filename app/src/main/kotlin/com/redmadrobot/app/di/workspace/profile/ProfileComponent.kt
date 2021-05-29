package com.redmadrobot.app.di.workspace.profile

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.network.workspaceApi.WorkspaceApiModule
import com.redmadrobot.app.di.workspace.userDataRepository.UserDataRepositoryModule
import com.redmadrobot.app.ui.workspace.profile.ProfileFragment
import dagger.Component

@Component(
    dependencies = [
        AppProvider::class,
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        ProfileViewModelModule::class,
        UserDataRepositoryModule::class,
        WorkspaceApiModule::class,
    ]
)
interface ProfileComponent {
    fun inject(obj: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(appProvider: AppProvider): ProfileComponent
    }

    companion object {
        fun init(appProvider: AppProvider): ProfileComponent {
            return DaggerProfileComponent.factory().create(appProvider)
        }
    }
}
