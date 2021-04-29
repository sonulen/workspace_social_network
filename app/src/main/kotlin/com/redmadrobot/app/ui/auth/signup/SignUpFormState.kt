package com.redmadrobot.app.ui.auth.signup

/**
 * Data validation state of the login form.
 */
data class SignUpFormState(
    val nicknameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val nameError: Int? = null,
    val surnameError: Int? = null,
    val birthDayError: Int? = null,
    val isDataValid: Boolean = false,
)
