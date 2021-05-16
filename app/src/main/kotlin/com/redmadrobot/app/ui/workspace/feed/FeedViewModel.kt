package com.redmadrobot.app.ui.workspace.feed

import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class FeedViewModel @Inject constructor() : BaseViewModel() {
    fun onFindFriendsClicked() {
        eventsQueue.offerEvent(EventMessage("Извини, но все разошлись!"))
    }
}
