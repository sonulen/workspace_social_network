package com.redmadrobot.app.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import com.redmadrobot.extensions.lifecycle.EventQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class BaseViewModel : ViewModel() {

    /**
     * LiveData для событий, которые должны быть обработаны один раз
     * Например: показы диалогов, снэкбаров с ошибками
     */
    val eventsQueue = EventQueue()

    /**
     * Scope для выполнения сетевых запросов
     */
    val ioScope = CoroutineScope(Dispatchers.IO)
}
