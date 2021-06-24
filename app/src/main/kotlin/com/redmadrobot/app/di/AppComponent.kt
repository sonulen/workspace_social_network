package com.redmadrobot.app.di

import android.app.Application
import com.redmadrobot.app.di.android.AndroidToolsComponent
import com.redmadrobot.app.di.android.AndroidToolsProvider
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryComponent
import com.redmadrobot.app.di.deauthorizationRepository.DeauthorizationRepositoryProvider
import com.redmadrobot.app.di.memoryCache.MemoryCacheComponent
import com.redmadrobot.app.di.memoryCache.MemoryCacheProvider
import com.redmadrobot.app.di.network.NetworkComponent
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryComponent
import com.redmadrobot.app.di.sessionRepository.SessionRepositoryProvider
import com.redmadrobot.app.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        AndroidToolsProvider::class,
        MemoryCacheProvider::class,
        NetworkProvider::class,
        SessionRepositoryProvider::class,
        DeauthorizationRepositoryProvider::class,
    ],
    modules = [
        MainViewModelModule::class,
        RootBeerModule::class,
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
            memoryCacheProvider: MemoryCacheProvider,
            deauthorizationRepositoryProvider: DeauthorizationRepositoryProvider,
        ): AppComponent
    }

    companion object {
        fun init(application: Application): AppComponent {
            val androidToolsProvider = AndroidToolsComponent.Builder.build(application)
            val networkProvider = NetworkComponent.Builder.build(androidToolsProvider)
            val sessionRepositoryProvider = SessionRepositoryComponent.Builder.build(androidToolsProvider)
            val memoryCacheProvider = MemoryCacheComponent.init()
            val deauthorizationRepositoryProvider =
                DeauthorizationRepositoryComponent.Builder.build(sessionRepositoryProvider, memoryCacheProvider)

            return DaggerAppComponent.factory().create(
                androidToolsProvider,
                networkProvider,
                sessionRepositoryProvider,
                memoryCacheProvider,
                deauthorizationRepositoryProvider,
            )
        }
    }
}
