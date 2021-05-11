package com.redmadrobot.app.ui.workspace.profile

import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : BaseViewModel() {
    fun onProfileEditClicked() {
        eventsQueue.offerEvent(EventMessage("Скоро починим"))
    }
}
