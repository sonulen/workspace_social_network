package com.redmadrobot.app.ui.workspace.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyController
import com.redmadrobot.app.di.qualifiers.Mock
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.ui.workspace.EventHideSwipeRefreshLoader
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
    private val liveState = MutableLiveData(FeedViewState())
    private var state: FeedViewState by liveState.delegate()

    init {
        controller.setListeners(
            emptyHandler = { eventsQueue.offerEvent(EventMessage("Извини, но все разошлись!")) },
            errorHandler = { onRefresh() },
            postLikeHandler = {
                onPostLikeChange(it.first, it.second)
            }
        )
        // Загрузим или обновим ленту
        refreshFeed()
        // Подпишимся на получение списка постов
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

    override fun onCleared() {
        controller.resetHandlers()
    }

    private fun onPostLikeChange(postId: String, isLiked: Boolean) {
        userDataRepository.changeLikePost(postId, !isLiked)
            .catch { e ->
                processError(e)
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

    fun onRefresh() {
        refreshFeed()
    }

    private fun refreshFeed() {
        userDataRepository.initFeed()
            .onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .catch { e ->
                processError(e)
            }
            .onEach {
                eventsQueue.offerEvent(EventHideSwipeRefreshLoader())
            }
            .launchIn(viewModelScope)
    }

    fun getEpoxyController(): EpoxyController = controller
}
