package com.redmadrobot.app.di.workspace.feed

import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.di.auth.authRepository.AuthRepositoryModule
import com.redmadrobot.app.di.network.authApi.AuthApiModule
import com.redmadrobot.app.di.network.workspaceApi.WorkspaceApiModule
import com.redmadrobot.app.di.workspace.userDataRepository.UserDataRepositoryModule
import com.redmadrobot.app.ui.workspace.feed.FeedFragment
import dagger.Component

@Component(
    dependencies = [
        AppProvider::class,
    ],
    modules = [
        AuthApiModule::class,
        AuthRepositoryModule::class,
        FeedViewModelModule::class,
        UserDataRepositoryModule::class,
        WorkspaceApiModule::class,
    ]
)
interface FeedComponent {
    fun inject(obj: FeedFragment)

    @Component.Factory
    interface Factory {
        fun create(appProvider: AppProvider): FeedComponent
    }

    companion object {
        fun init(appProvider: AppProvider): FeedComponent {
            return DaggerFeedComponent.factory().create(appProvider)
        }
    }
}
