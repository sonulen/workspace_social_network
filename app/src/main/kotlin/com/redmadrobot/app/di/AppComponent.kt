package com.redmadrobot.app.di

import android.app.Application
import com.redmadrobot.app.di.android.AndroidToolsComponent
import com.redmadrobot.app.di.android.AndroidToolsProvider
import com.redmadrobot.app.di.network.NetworkComponent
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryComponent
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkProvider::class, AndroidToolsProvider::class, SessionRepositoryProvider::class],
    modules = [
        MainViewModelModule::class,
    ]
)
interface AppComponent : AppProvider {

    fun inject(obj: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            androidToolsProvider: AndroidToolsProvider,
            networkProvider: NetworkProvider,
            sessionRepositoryProvider: SessionRepositoryProvider,
        ): AppComponent
    }

    companion object {
        fun init(application: Application): AppComponent {
            val androidToolsProvider = AndroidToolsComponent.Builder.build(application)
            val networkProvider = NetworkComponent.Builder.build()
            val sessionRepositoryProvider = SessionRepositoryComponent.Builder.build(androidToolsProvider)

            return DaggerAppComponent.factory()
                .create(androidToolsProvider, networkProvider, sessionRepositoryProvider)
        }
    }
}