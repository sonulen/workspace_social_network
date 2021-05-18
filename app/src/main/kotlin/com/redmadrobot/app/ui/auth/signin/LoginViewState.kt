package com.redmadrobot.app.ui.auth.signin

import com.redmadrobot.app.ui.base.viewmodel.ScreenState

/**
 * Состояние валидности данных на форме логина
 */
data class LoginViewState(
    val screenState: ScreenState = ScreenState.CONTENT,
    val isEmailValid: Boolean = false,
    val emailError: Int? = null,
    val isPasswordValid: Boolean = false,
    val passwordError: Int? = null,
)
