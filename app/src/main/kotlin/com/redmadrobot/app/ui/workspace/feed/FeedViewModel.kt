package com.redmadrobot.app.ui.workspace.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : BaseViewModel() {
    private val liveState = MutableLiveData<FeedViewState>(FeedViewState())
    private var state: FeedViewState by liveState.delegate()

    init {
        userDataRepository.initFeed()
            .catch { e ->
                processError(e)
            }
            .onEach { /* No-op */ }
            .launchIn(viewModelScope)
    }

    private fun processError(e: Throwable) {
        state = state.copy(screenState = ScreenState.ERROR)
        if (e is NetworkException) {
            eventsQueue.offerEvent(EventError(e.message))
        }
    }

    fun onFindFriendsClicked() {
        eventsQueue.offerEvent(EventMessage("Извини, но все разошлись!"))
    }
}
