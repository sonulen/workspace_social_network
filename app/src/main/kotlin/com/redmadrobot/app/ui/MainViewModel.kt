package com.redmadrobot.app.ui

import com.redmadrobot.app.ui.auth.WelcomeFragmentDirections
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.repository.SessionRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val sessionRepository: SessionRepository) : BaseViewModel() {
    fun requestDirections() {
        eventsQueue.offerEvent(
            EventNavigateTo(
                if (sessionRepository.sessionExists()) {
                    WelcomeFragmentDirections.toWorkspaceGraph()
                } else {
                    WelcomeFragmentDirections.startAuthGraph()
                }
            )
        )
    }
}
