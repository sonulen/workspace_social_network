package com.redmadrobot.app.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.EventQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    /**
     * Добавляет событие в очередь через Dispatchers.Main
     *
     * @param event Добавляемое событие
     */
    suspend fun offerOnMain(event: Event) {
        withContext(Dispatchers.Main) {
            eventsQueue.offerEvent(event)
        }
    }
}
