package com.redmadrobot.app.ui.workspace.profileEdit

import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.ui.workspace.profile.UserDataProfileViewState

data class ValidAndError(
    val isValid: Boolean = false,
    val error: Int? = null,
)

data class ProfileEditViewState(
    val screenState: ScreenState = ScreenState.LOADING,
    val userData: UserDataProfileViewState = UserDataProfileViewState(),
    val userDataForm: UserDataProfileViewState = UserDataProfileViewState(),
    val isSaveButtonEnable: Boolean = true,
    val nickname: ValidAndError = ValidAndError(false, null),
    val name: ValidAndError = ValidAndError(false, null),
    val surname: ValidAndError = ValidAndError(false, null),
    val birthDay: ValidAndError = ValidAndError(false, null),
)
