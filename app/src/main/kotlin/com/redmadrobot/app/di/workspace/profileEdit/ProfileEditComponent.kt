package com.redmadrobot.app.di.workspace.profileEdit

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryProvider
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.network.workspaceApi.WorkspaceApiModule
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.di.workspace.userDataRepository.UserDataRepositoryModule
import com.redmadrobot.app.ui.workspace.profileEdit.ProfileEditFragment
import dagger.Component

@Component(
    dependencies = [
        DeauthorizationRepositoryProvider::class,
        MapMemoryProvider::class,
        NetworkProvider::class,
        SessionRepositoryProvider::class,
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        AuthValidatorModule::class,
        ProfileEditViewModelModule::class,
        UserDataRepositoryModule::class,
        WorkspaceApiModule::class,
    ]
)
interface ProfileEditComponent {
    fun inject(obj: ProfileEditFragment)

    @Component.Factory
    interface Factory {
        fun create(
            networkProvider: NetworkProvider,
            mapMemoryProvider: MapMemoryProvider,
            sessionRepositoryProvider: SessionRepositoryProvider,
            deauthorizationRepositoryProvider: DeauthorizationRepositoryProvider,
        ): ProfileEditComponent
    }

    companion object {
        fun init(appProvider: AppProvider): ProfileEditComponent {
            return DaggerProfileEditComponent.factory()
                .create(appProvider, appProvider, appProvider, appProvider)
        }
    }
}
