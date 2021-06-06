package com.redmadrobot.app.ui.workspace.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyController
import com.redmadrobot.app.di.qualifiers.Mock
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.ui.workspace.PostsEpoxyController
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    @Mock private val userDataRepository: UserDataRepository,
    private val controller: PostsEpoxyController,
) : BaseViewModel() {
    private val liveState = MutableLiveData<FeedViewState>(FeedViewState())
    private var state: FeedViewState by liveState.delegate()

    init {
        userDataRepository.initFeed()
            .catch { e ->
                processError(e)
            }
            .onStart {
                controller.setEmptyView()
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .onEach { /* No-op */ }
            .launchIn(viewModelScope)

        userDataRepository.getUserFeed()
            .onEach { feed ->
                if (feed.isEmpty()) {
                    controller.setEmptyView()
                } else {
                    controller.setPostsList(feed)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun processError(e: Throwable) {
        state = state.copy(screenState = ScreenState.ERROR)
        controller.setErrorView()
        if (e is NetworkException) {
            eventsQueue.offerEvent(EventError(e.message))
        }
    }

    fun getEpoxyController(): EpoxyController = controller
}
