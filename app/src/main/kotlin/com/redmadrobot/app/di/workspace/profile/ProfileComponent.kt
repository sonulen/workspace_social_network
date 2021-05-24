package com.redmadrobot.app.di.workspace.profile

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.workspace.userDataRepository.UserDataRepositoryModule
import com.redmadrobot.app.ui.workspace.profile.ProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        MapMemoryProvider::class,
    ],
    modules = [
        UserDataRepositoryModule::class,
        ProfileViewModelModule::class,
    ]
)
interface ProfileComponent {
    fun inject(obj: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            mapMemoryProvider: MapMemoryProvider,
        ): ProfileComponent
    }

    companion object {
        fun init(appProvider: AppProvider): ProfileComponent {
            return DaggerProfileComponent.factory().create(appProvider)
        }
    }
}
