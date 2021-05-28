package com.redmadrobot.app.di.workspace.profile

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryModule
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.network.workspaceApi.WorkspaceApiModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.workspace.userDataRepository.UserDataRepositoryModule
import com.redmadrobot.app.ui.workspace.profile.ProfileFragment
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
        DeauthorizationRepositoryModule::class,
        ProfileViewModelModule::class,
        UserDataRepositoryModule::class,
        WorkspaceApiModule::class,
    ]
)
interface ProfileComponent {
    fun inject(obj: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            sessionRepository: SessionRepositoryProvider,
            networkProvider: NetworkProvider,
            mapMemoryProvider: MapMemoryProvider,
        ): ProfileComponent
    }

    companion object {
        fun init(appProvider: AppProvider): ProfileComponent {
            return DaggerProfileComponent.factory().create(appProvider, appProvider, appProvider)
        }
    }
}
