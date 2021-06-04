package com.redmadrobot.app.ui.workspace.profile

import com.redmadrobot.app.ui.base.viewmodel.ScreenState

data class ProfileViewState(
    val screenState: ScreenState = ScreenState.LOADING,
    val userData: UserDataProfileViewState = UserDataProfileViewState(),
)
