package com.redmadrobot.app.di.workspace.profileEdit

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.ui.workspace.profileEdit.ProfileEditFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
//        SessionRepositoryProvider::class,
//        NetworkProvider::class,
    ],
    modules = [
        ProfileEditViewModelModule::class
    ]
)
interface ProfileEditComponent {
    fun inject(obj: ProfileEditFragment)

    @Component.Factory
    interface Factory {
        fun create(
//            sessionRepository: SessionRepositoryProvider,
//            networkProvider: NetworkProvider,
        ): ProfileEditComponent
    }

    companion object {
        fun init(appProvider: AppProvider): ProfileEditComponent {
            return DaggerProfileEditComponent.factory()
                .create()
        }
    }
}
