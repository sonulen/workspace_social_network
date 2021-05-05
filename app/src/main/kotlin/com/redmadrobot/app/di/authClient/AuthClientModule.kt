package com.redmadrobot.app.di.authClient

import android.content.SharedPreferences
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthClientModule {
    @Provides
    @Singleton
    fun provideAuthClient(prefs: SharedPreferences): AuthRepository = AuthRepositoryImpl(prefs)
}
