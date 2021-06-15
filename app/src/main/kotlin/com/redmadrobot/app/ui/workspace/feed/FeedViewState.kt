package com.redmadrobot.app.ui.workspace.feed

import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.domain.entity.repository.Post

data class FeedViewState(
    val screenState: ScreenState = ScreenState.LOADING,
    val feed: List<Post> = emptyList(),
)
