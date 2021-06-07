@file:Suppress("TooManyFunctions")

package com.redmadrobot.basetest.fakes

import android.content.SharedPreferences
import java.util.concurrent.ConcurrentHashMap

class EditorFake(private var source: ConcurrentHashMap<String, Any?>) : SharedPreferences.Editor {
    private val temp: ConcurrentHashMap<String, Any?> = source

    override fun putString(key: String?, value: String?): SharedPreferences.Editor = put(key, value)

    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor = put(key, values)

    override fun putInt(key: String?, value: Int): SharedPreferences.Editor = put(key, value)

    override fun putLong(key: String?, value: Long): SharedPreferences.Editor = put(key, value)

    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor = put(key, value)

    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor = put(key, value)

    private fun put(key: String?, value: Any?): SharedPreferences.Editor {
        if (key != null) {
            temp[key] = value
        }
        return this
    }

    override fun remove(key: String?): SharedPreferences.Editor {
        if (key != null) {
            temp.remove(key)
        }
        return this
    }

    override fun clear(): SharedPreferences.Editor {
        temp.clear()
        return this
    }

    override fun commit(): Boolean {
        source = temp
        return true
    }

    override fun apply() {
        source = temp
    }
}
