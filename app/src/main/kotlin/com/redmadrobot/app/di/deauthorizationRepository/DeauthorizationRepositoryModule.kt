package com.redmadrobot.app.di.deauthorizationRepository

import com.redmadrobot.data.repository.DeauthorizationRepositoryImpl
import com.redmadrobot.domain.repository.DeauthorizationRepository
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.mapmemory.MapMemory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DeauthorizationRepositoryModule {
    @Provides
    @Singleton
    fun provideDeauthorizationRepository(
        sessionRepository: SessionRepository,
        mapMemory: MapMemory,
    ): DeauthorizationRepository = DeauthorizationRepositoryImpl(sessionRepository, mapMemory)
}
