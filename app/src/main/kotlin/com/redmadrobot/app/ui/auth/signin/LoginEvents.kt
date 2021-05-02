package com.redmadrobot.app.ui.auth.signin

import com.redmadrobot.extensions.lifecycle.Event

/**
 * Событие об успешном логине
 */
class EventLoginSuccess : Event

/**
 * Событие об провале логина
 */
class EventLoginFailed : Event

/**
 * Событие об изменении валидности данных формы
 */
class EventLoginFormStateChanged : Event
