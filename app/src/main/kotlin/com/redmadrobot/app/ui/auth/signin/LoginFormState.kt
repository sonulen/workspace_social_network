package com.redmadrobot.app.ui.auth.signin

/**
 * Состояние валидности данных на форме логина
 */
class LoginFormState(
    var emailError: Int? = null,
    var passwordError: Int? = null,
    var isDataValid: Boolean = true,
)
