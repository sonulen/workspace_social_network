package com.redmadrobot.app.di

import android.app.Application
import com.redmadrobot.app.di.android.AndroidToolsComponent
import com.redmadrobot.app.di.android.AndroidToolsProvider
import com.redmadrobot.app.di.authClient.AuthClientComponent
import com.redmadrobot.app.di.authClient.AuthClientProvider
import com.redmadrobot.app.di.network.NetworkComponent
import com.redmadrobot.app.di.network.NetworkProvider
import com.redmadrobot.app.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkProvider::class, AndroidToolsProvider::class, AuthClientProvider::class],
)
interface AppComponent : AppProvider {

    fun inject(obj: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            androidToolsProvider: AndroidToolsProvider,
            networkProvider: NetworkProvider,
            authClientProvider: AuthClientProvider,
        ): AppComponent
    }

    companion object {
        fun init(application: Application): AppComponent {
            val androidToolsProvider = AndroidToolsComponent.Builder.build(application)
            val networkProvider = NetworkComponent.Builder.build()
            val authClientProvider = AuthClientComponent.Builder.build(androidToolsProvider)

            return DaggerAppComponent.factory()
                .create(androidToolsProvider, networkProvider, authClientProvider)
        }
    }
}
