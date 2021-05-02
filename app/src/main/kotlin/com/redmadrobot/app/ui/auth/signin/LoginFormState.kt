package com.redmadrobot.app.ui.auth.signin

/**
 * Состояние валидности данных на форме логина
 */
data class LoginFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
)
