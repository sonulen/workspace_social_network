package com.redmadrobot.app.ui.auth.done

import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class DoneViewModel @Inject constructor() : BaseViewModel() {
    fun onGoToFeedClicked() {
        eventsQueue.offerEvent(EventNavigateTo(DoneFragmentDirections.toWorkspaceGraph()))
    }
}
