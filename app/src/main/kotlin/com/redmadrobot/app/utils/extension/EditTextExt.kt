package com.redmadrobot.app.utils.extension

import android.widget.EditText

fun EditText.setTextIfDifferent(string: String) {
    if (string != text.toString()) {
        setText(string)
    }
}
