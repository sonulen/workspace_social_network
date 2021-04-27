package com.redmadrobot.domain.entity.repository.signup

/**
 * Data validation state of the login form.
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
