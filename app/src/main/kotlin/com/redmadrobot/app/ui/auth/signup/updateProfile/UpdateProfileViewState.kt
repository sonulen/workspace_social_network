package com.redmadrobot.app.ui.auth.signup.updateProfile

import com.redmadrobot.app.ui.base.viewmodel.ScreenState

data class ValidAndError(
    val isValid: Boolean = false,
    val error: Int? = null,
)

/**
 * Состояние валидности данных на форме обновления профиля
 */
data class UpdateProfileViewState(
    val screenState: ScreenState = ScreenState.CONTENT,
    val email: String? = null,
    val password: String? = null,
    val nickname: ValidAndError = ValidAndError(false, null),
    val name: ValidAndError = ValidAndError(false, null),
    val surname: ValidAndError = ValidAndError(false, null),
    val birthDay: ValidAndError = ValidAndError(false, null),
)
