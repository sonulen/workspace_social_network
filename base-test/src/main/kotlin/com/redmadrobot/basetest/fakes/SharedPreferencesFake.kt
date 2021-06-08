@file:Suppress(
    "NotImplementedDeclaration",
    "StringLiteralDuplication",
)
package com.redmadrobot.basetest.fakes

import android.content.SharedPreferences
import java.util.concurrent.ConcurrentHashMap

class SharedPreferencesFake : SharedPreferences {
    private val storage = ConcurrentHashMap<String, Any?>()

    override fun getAll(): MutableMap<String, *> = storage

    override fun getString(key: String?, defValue: String?): String? = storage[key] as? String ?: defValue

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getInt(key: String?, defValue: Int): Int = storage[key] as? Int ?: defValue

    override fun getLong(key: String?, defValue: Long): Long = storage[key] as? Long ?: defValue

    override fun getFloat(key: String?, defValue: Float): Float = storage[key] as? Float ?: defValue

    override fun getBoolean(key: String?, defValue: Boolean): Boolean = storage[key] as? Boolean ?: defValue

    override fun contains(key: String?): Boolean = if (key != null) storage.contains(key) else false

    override fun edit(): SharedPreferences.Editor = EditorFake(storage)

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        TODO("Not yet implemented")
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        TODO("Not yet implemented")
    }
}
