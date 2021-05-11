package com.redmadrobot.app.ui.workspace.feed

import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class FeedViewModel @Inject constructor() : BaseViewModel() {
    fun onLogoutClicked() {
        eventsQueue.offerEvent(EventNavigateTo(FeedFragmentDirections.toWelcomeFragment()))
    }
}
