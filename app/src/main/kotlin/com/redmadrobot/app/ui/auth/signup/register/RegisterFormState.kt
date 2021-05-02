package com.redmadrobot.app.ui.auth.signup.register

/**
 * Состояние валидности данных на форме регистрации
 */
data class RegisterFormState(
    val nicknameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val nameError: Int? = null,
    val surnameError: Int? = null,
    val birthDayError: Int? = null,
    val isDataValid: Boolean = false,
)
