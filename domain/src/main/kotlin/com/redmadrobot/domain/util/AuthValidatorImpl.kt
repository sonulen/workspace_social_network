package com.redmadrobot.domain.util

import java.util.regex.Pattern

class AuthValidatorImpl : AuthValidator {
    private val nicknamePatter = Pattern.compile("^[a-zA-Z0-9]*\$")
    private val namePatter = Pattern.compile("^[a-zA-Z]*\$")
    private val datePattern = Pattern.compile("^([12][90]\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))\$")
    private val passwordPattern = Pattern.compile("^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}\$")

    override fun isEmailValid(email: String): Boolean {
        return email != "" && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isPasswordValid(password: String): Boolean {
        return password != "" && passwordPattern.matcher(password).matches()
    }

    override fun isNicknameValid(nickName: String): Boolean {
        return nickName != "" && nicknamePatter.matcher(nickName).matches()
    }

    override fun isNameValid(name: String): Boolean {
        return name != "" && namePatter.matcher(name).matches()
    }

    override fun isSurNameValid(surname: String): Boolean {
        return surname != "" && namePatter.matcher(surname).matches()
    }

    override fun isBirthDayValid(birthDay: String): Boolean {
        return birthDay != "" && datePattern.matcher(birthDay).matches()
    }
}
