package com.redmadrobot.domain.util

import java.util.regex.Pattern

class AuthValidatorImpl : AuthValidator {

    override fun isEmailValid(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    override fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}\$"
        return Pattern.compile(passwordPattern).matcher(password).matches()
    }

    override fun isNicknameValid(nickName: String): Boolean = nickName.isNotEmpty()
    override fun isNameValid(name: String): Boolean = name.isNotEmpty()
    override fun isSurNameValid(surname: String): Boolean = surname.isNotEmpty()
    override fun isBirthDayValid(birthDay: String): Boolean = birthDay.isNotEmpty()
}
