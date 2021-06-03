package com.redmadrobot.app.ui.auth.signup.updateProfile

import com.redmadrobot.app.ui.base.viewmodel.ScreenState

data class InputField(
    val value: String = "",
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
    val nickname: InputField = InputField(),
    val name: InputField = InputField(),
    val surname: InputField = InputField(),
    val birthDay: InputField = InputField(),
)
