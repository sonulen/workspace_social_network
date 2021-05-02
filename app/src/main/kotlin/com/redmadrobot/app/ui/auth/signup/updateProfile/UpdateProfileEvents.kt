package com.redmadrobot.app.ui.auth.signup.updateProfile

import com.redmadrobot.extensions.lifecycle.Event

/**
 * Событие об успешном обновлении профиля
 */
class EventUpdateProfileSuccess : Event

/**
 * Событие об провале обновлении профиля
 */
class EventUpdateProfileFailed : Event

/**
 * Событие об изменении валидности данных формы обновления профиля
 */
class EventUpdateProfileFormStateChanged : Event
