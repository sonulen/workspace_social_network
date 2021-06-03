package com.redmadrobot.app.ui.auth.signup.register

import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.utils.InputField

/**
 * Состояние валидности данных на форме регистрации
 */
data class RegisterViewState(
    val screenState: ScreenState = ScreenState.CONTENT,
    val email: InputField = InputField(),
    val password: InputField = InputField(),
)
