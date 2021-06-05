@file:Suppress("TooManyFunctions")

package com.redmadrobot.basetest.fakes

import android.content.SharedPreferences
import java.util.concurrent.ConcurrentHashMap

class EditorFake(private var source: ConcurrentHashMap<String, Any?>) : SharedPreferences.Editor {
    private val temp: ConcurrentHashMap<String, Any?> = source

    /**
     * Set a String value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.  Passing `null`
     * for this argument is equivalent to calling [.remove] with
     * this key.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun putString(key: String?, value: String?): SharedPreferences.Editor = put(key, value)

    /**
     * Set a set of String values in the preferences editor, to be written
     * back once [.commit] or [.apply] is called.
     *
     * @param key The name of the preference to modify.
     * @param values The set of new values for the preference.  Passing `null`
     * for this argument is equivalent to calling [.remove] with
     * this key.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor = put(key, values)

    /**
     * Set an int value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun putInt(key: String?, value: Int): SharedPreferences.Editor = put(key, value)

    /**
     * Set a long value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun putLong(key: String?, value: Long): SharedPreferences.Editor = put(key, value)

    /**
     * Set a float value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor = put(key, value)

    /**
     * Set a boolean value in the preferences editor, to be written back
     * once [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor = put(key, value)

    private fun put(key: String?, value: Any?): SharedPreferences.Editor {
        if (key != null) {
            temp[key] = value
        }
        return this
    }

    /**
     * Mark in the editor that a preference value should be removed, which
     * will be done in the actual preferences once [.commit] is
     * called.
     *
     *
     * Note that when committing back to the preferences, all removals
     * are done first, regardless of whether you called remove before
     * or after put methods on this editor.
     *
     * @param key The name of the preference to remove.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun remove(key: String?): SharedPreferences.Editor {
        if (key != null) {
            temp.remove(key)
        }
        return this
    }

    /**
     * Mark in the editor to remove *all* values from the
     * preferences.  Once commit is called, the only remaining preferences
     * will be any that you have defined in this editor.
     *
     *
     * Note that when committing back to the preferences, the clear
     * is done first, regardless of whether you called clear before
     * or after put methods on this editor.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    override fun clear(): SharedPreferences.Editor {
        temp.clear()
        return this
    }

    /**
     * Commit your preferences changes back from this Editor to the
     * [SharedPreferences] object it is editing.  This atomically
     * performs the requested modifications, replacing whatever is currently
     * in the SharedPreferences.
     *
     *
     * Note that when two editors are modifying preferences at the same
     * time, the last one to call commit wins.
     *
     *
     * If you don't care about the return value and you're
     * using this from your application's main thread, consider
     * using [.apply] instead.
     *
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    override fun commit(): Boolean {
        source = temp
        return true
    }

    /**
     * Commit your preferences changes back from this Editor to the
     * [SharedPreferences] object it is editing.  This atomically
     * performs the requested modifications, replacing whatever is currently
     * in the SharedPreferences.
     *
     *
     * Note that when two editors are modifying preferences at the same
     * time, the last one to call apply wins.
     *
     *
     * Unlike [.commit], which writes its preferences out
     * to persistent storage synchronously, [.apply]
     * commits its changes to the in-memory
     * [SharedPreferences] immediately but starts an
     * asynchronous commit to disk and you won't be notified of
     * any failures.  If another editor on this
     * [SharedPreferences] does a regular [.commit]
     * while a [.apply] is still outstanding, the
     * [.commit] will block until all async commits are
     * completed as well as the commit itself.
     *
     *
     * As [SharedPreferences] instances are singletons within
     * a process, it's safe to replace any instance of [.commit] with
     * [.apply] if you were already ignoring the return value.
     *
     *
     * You don't need to worry about Android component
     * lifecycles and their interaction with `apply()`
     * writing to disk.  The framework makes sure in-flight disk
     * writes from `apply()` complete before switching
     * states.
     *
     *
     * The SharedPreferences.Editor interface
     * isn't expected to be implemented directly.  However, if you
     * previously did implement it and are now getting errors
     * about missing `apply()`, you can simply call
     * [.commit] from `apply()`.
     */
    override fun apply() {
        source = temp
    }
}
