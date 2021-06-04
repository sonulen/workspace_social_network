package com.redmadrobot.app.ui

import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.RootGraphDirections
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.repository.DeauthorizationRepository
import com.redmadrobot.domain.repository.EventDeauthorization.Logout
import com.redmadrobot.domain.repository.SessionRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val deauthorizationRepository: DeauthorizationRepository,
) : BaseViewModel() {

    init {
        deauthorizationRepository.getDeauthorizationEventStream()
            .onEach {
                if (it is Logout) {
                    eventsQueue.offerEvent(EventError(it.message))
                    eventsQueue.offerEvent(EventNavigateTo(RootGraphDirections.toAuthGraph()))
                }
            }
            .launchIn(viewModelScope)
    }

    fun requestDirections() {
        eventsQueue.offerEvent(
            EventNavigateTo(
                if (sessionRepository.sessionExists()) {
                    RootGraphDirections.toWorkspaceGraph()
                } else {
                    RootGraphDirections.toAuthGraph()
                }
            )
        )
    }
}
