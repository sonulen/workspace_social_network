package com.redmadrobot.data.repository

import com.redmadrobot.domain.repository.DeauthorizationRepository
import com.redmadrobot.domain.repository.EventDeauthorization
import com.redmadrobot.domain.repository.SessionRepository
import com.redmadrobot.mapmemory.MapMemory
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DeauthorizationRepositoryImpl @Inject constructor(
    private val session: SessionRepository,
    private val memory: MapMemory,
) : DeauthorizationRepository {
    private val deauthorizationEvents = MutableSharedFlow<EventDeauthorization>()

    override fun logout(message: String): Flow<Unit> = flow {
        session.clear()
        memory.clear()
        deauthorizationEvents.emit(EventDeauthorization.Logout(message))
        emit(Unit)
    }

    override fun getDeauthorizationEventStream(): SharedFlow<EventDeauthorization> =
        deauthorizationEvents.asSharedFlow()
}
