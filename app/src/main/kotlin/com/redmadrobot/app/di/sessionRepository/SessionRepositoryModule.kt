package com.redmadrobot.app.di.sessionRepository

import android.content.SharedPreferences
import com.redmadrobot.data.repository.SessionRepositoryImpl
import com.redmadrobot.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SessionRepositoryModule {
    @Provides
    @Singleton
    fun provideSessionRepository(prefs: SharedPreferences): SessionRepository = SessionRepositoryImpl(prefs)
}
