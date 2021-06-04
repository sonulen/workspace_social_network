package com.redmadrobot.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface DeauthorizationRepository {
    fun logout(message: String): Flow<Unit>
    fun getDeauthorizationEventStream(): SharedFlow<EventDeauthorization>
}
