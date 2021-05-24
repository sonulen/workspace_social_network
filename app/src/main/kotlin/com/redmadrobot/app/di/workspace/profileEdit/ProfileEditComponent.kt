package com.redmadrobot.app.di.workspace.profileEdit

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.mapMemory.MapMemoryProvider
import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.di.workspace.userDataRepository.UserDataRepositoryModule
import com.redmadrobot.app.ui.workspace.profileEdit.ProfileEditFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
//        SessionRepositoryProvider::class,
//        NetworkProvider::class,
        MapMemoryProvider::class,
    ],
    modules = [
        UserDataRepositoryModule::class,
        ProfileEditViewModelModule::class,
        AuthValidatorModule::class
    ]
)
interface ProfileEditComponent {
    fun inject(obj: ProfileEditFragment)

    @Component.Factory
    interface Factory {
        fun create(
//            sessionRepository: SessionRepositoryProvider,
//            networkProvider: NetworkProvider,
            mapMemoryProvider: MapMemoryProvider,
        ): ProfileEditComponent
    }

    companion object {
        fun init(appProvider: AppProvider): ProfileEditComponent {
            return DaggerProfileEditComponent.factory()
                .create(appProvider)
        }
    }
}
