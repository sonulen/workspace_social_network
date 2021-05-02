package com.redmadrobot.app.ui.auth.signup.updateProfile

/**
 * Состояние валидности данных на форме обновления профиля
 */
data class UpdateProfileFormState(
    val nicknameError: Int? = null,
    val nameError: Int? = null,
    val surnameError: Int? = null,
    val birthDayError: Int? = null,
    val isDataValid: Boolean = false,
)
