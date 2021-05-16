package com.redmadrobot.app.ui.workspace.profile.mine

import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ProfileMineEmptyFriendsViewModel @Inject constructor() : BaseViewModel() {
    fun onFindFriendsClicked() {
        eventsQueue.offerEvent(EventMessage("Скоро починим!"))
    }
}
