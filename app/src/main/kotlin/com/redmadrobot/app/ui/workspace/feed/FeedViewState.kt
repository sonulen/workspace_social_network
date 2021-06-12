package com.redmadrobot.app.ui.workspace.feed

import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.domain.entity.repository.Feed

data class FeedViewState(
    val screenState: ScreenState = ScreenState.LOADING,
    val feed: Feed = emptyList(),
)
