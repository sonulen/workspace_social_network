package com.redmadrobot.app.ui.auth.signin

import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.utils.InputField

/**
 * Состояние валидности данных на форме логина
 */
data class LoginViewState(
    val screenState: ScreenState = ScreenState.CONTENT,
    val email: InputField = InputField(),
    val password: InputField = InputField(),
)
