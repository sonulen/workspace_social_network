package com.redmadrobot.app.ui.auth.signup.register

/**
 * Состояние валидности данных на форме регистрации
 */
class RegisterFormState(
    var emailError: Int? = null,
    var passwordError: Int? = null,
    var isDataValid: Boolean = true,
)
