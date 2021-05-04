package com.redmadrobot.app.ui.auth.signup.updateProfile

/**
 * Состояние валидности данных на форме обновления профиля
 */
class UpdateProfileFormState(
    var nicknameError: Int? = null,
    var nameError: Int? = null,
    var surnameError: Int? = null,
    var birthDayError: Int? = null,
    var isDataValid: Boolean = true,
)
