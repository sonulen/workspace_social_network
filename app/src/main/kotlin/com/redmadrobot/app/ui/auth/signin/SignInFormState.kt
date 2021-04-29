package com.redmadrobot.app.ui.auth.signin

/**
 * Data validation state of the login form.
 */
data class SignInFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
)
