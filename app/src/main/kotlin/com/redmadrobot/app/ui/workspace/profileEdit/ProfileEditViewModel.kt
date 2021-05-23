package com.redmadrobot.app.ui.workspace.profileEdit

import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ProfileEditViewModel @Inject constructor() : BaseViewModel() {
    fun onSaveClicked() {
        TODO("Not yet implemented")
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(ProfileEditFragmentDirections.profileEditFragmentPop()))
    }
}
