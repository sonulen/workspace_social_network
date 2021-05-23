package com.redmadrobot.app.ui.workspace.profile

import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : BaseViewModel() {
    fun onProfileEditClicked() {
        eventsQueue.offerEvent(EventNavigateTo(ProfileFragmentDirections.toProfileEditFragment()))
    }

    fun onLogoutClicked() {
        eventsQueue.offerEvent(EventNavigateTo(ProfileFragmentDirections.toWelcomeFragment()))
    }
}
