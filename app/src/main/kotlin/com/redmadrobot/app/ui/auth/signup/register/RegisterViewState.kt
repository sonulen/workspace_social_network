package com.redmadrobot.app.ui.auth.signup.register

import com.redmadrobot.app.ui.base.viewmodel.ScreenState

/**
 * Состояние валидности данных на форме регистрации
 */
data class RegisterViewState(
    val screenState: ScreenState = ScreenState.CONTENT,
    val isEmailValid: Boolean = false,
    val emailError: Int? = null,
    val isPasswordValid: Boolean = false,
    val passwordError: Int? = null,
)
