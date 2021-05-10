package com.redmadrobot.app.ui.base

import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.utils.extension.onNext
import com.redmadrobot.extensions.lifecycle.requireValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Делегат для работы с содержимым [MutableLiveData] как с полем.
 *
 * Без делегата пришлось бы писать такой код:
 * ```
 *  val liveState = MutableLiveData<IntroViewState>(initialState)
 *  var state: IntroViewState
 *      get() = liveState.requireValue()
 *      set(value) = liveState.onNext(value)
 * ```
 * С делегатом для такой же логики достаточно написать:
 * ```
 *  val liveState = MutableLiveData<IntroViewState>(initialState)
 *  var state: IntroViewState by liveState.delegate()
 * ```
 */
fun <T : Any> MutableLiveData<T>.delegate(): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            onNext(value)
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): T = requireValue()
    }
}
