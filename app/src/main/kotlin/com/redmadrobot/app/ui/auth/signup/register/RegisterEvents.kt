package com.redmadrobot.app.ui.auth.signup.register

import com.redmadrobot.extensions.lifecycle.Event

/**
 * Событие об успешной регистрации
 */
class EventRegisterSuccess : Event

/**
 * Событие об провале регистрации
 */
class EventRegisterFailed : Event

/**
 * Событие об изменении валидности данных формы регистрации
 */
class EventRegisterFormStateChanged : Event
