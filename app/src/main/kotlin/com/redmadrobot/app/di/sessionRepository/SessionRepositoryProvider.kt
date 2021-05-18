package com.redmadrobot.app.di.sessionRepository

import com.redmadrobot.domain.repository.SessionRepository

interface SessionRepositoryProvider {
    fun sessionRepository(): SessionRepository
}
