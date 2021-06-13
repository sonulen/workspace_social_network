package com.redmadrobot.app.ui

import android.content.Context
import android.os.Debug
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.BuildConfig
import com.redmadrobot.app.R
import com.redmadrobot.app.RootGraphDirections
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.repository.DeauthorizationRepository
import com.redmadrobot.domain.repository.EventDeauthorization.Logout
import com.redmadrobot.domain.repository.SessionRepository
import com.scottyab.rootbeer.RootBeer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val deauthorizationRepository: DeauthorizationRepository,
    private val rootBeer: RootBeer,
    private val context: Context,
) : BaseViewModel() {

    init {
        checkEnvironment()

        deauthorizationRepository.getDeauthorizationEventStream()
            .onEach {
                if (it is Logout) {
                    eventsQueue.offerEvent(EventError(it.message))
                    eventsQueue.offerEvent(EventNavigateTo(RootGraphDirections.toAuthGraph()))
                }
            }
            .launchIn(viewModelScope)
    }

    private fun checkEnvironment() {
        if (rootBeer.isRooted) {
            eventsQueue.offerEvent(EventError(
                context.getString(R.string.root_env_message)
            ))
        }
        if (BuildConfig.DEBUG || Debug.isDebuggerConnected()) {
            eventsQueue.offerEvent(EventError(
                context.getString(R.string.debug_mode_message)
            ))
        }
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
