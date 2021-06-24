package com.redmadrobot.app.ui.base.events

import android.support.annotation.StringRes
import com.redmadrobot.extensions.lifecycle.Event

sealed class ErrorMessage {
    data class Text(
        val message: String,
    ) : ErrorMessage()

    data class Id(
        @StringRes val messageId: Int,
    ) : ErrorMessage()
}

class EventError(val errorMessage: ErrorMessage) : Event
