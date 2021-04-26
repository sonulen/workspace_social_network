package com.redmadrobot.app.utils.validate

import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun isValidPassword(password: String): Boolean {
    val pattern: Pattern
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,}$"
    pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

fun isValidNickname(nickName: String): Boolean {
    return nickName.isNotEmpty()
}
