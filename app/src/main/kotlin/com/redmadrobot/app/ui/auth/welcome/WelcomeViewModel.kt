package com.redmadrobot.app.ui.auth.welcome

import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : BaseViewModel() {
    fun onGoToLoginClicked() {
        eventsQueue.offerEvent(EventNavigateTo(WelcomeFragmentDirections.toLoginFragment()))
    }

    fun onGoToRegisterClicked() {
        eventsQueue.offerEvent(EventNavigateTo(WelcomeFragmentDirections.toRegisterFragment()))
    }
}
