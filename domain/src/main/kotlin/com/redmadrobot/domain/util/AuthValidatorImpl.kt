package com.redmadrobot.domain.util

import java.util.regex.Pattern

class AuthValidatorImpl : AuthValidator {
    private val nicknamePatter = Pattern.compile("^[a-zA-Z0-9]*\$")
    private val namePatter = Pattern.compile("^[a-zA-Z]*\$")
    private val datePattern = Pattern.compile("^([12][90]\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))\$")
    private val passwordPattern = Pattern.compile("^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}\$")
    private val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    )

    override fun isEmailValid(email: String): Boolean {
        return email.isNotBlank() && emailPattern.matcher(email).matches()
    }

    override fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && passwordPattern.matcher(password).matches()
    }

    override fun isNicknameValid(nickName: String): Boolean {
        return nickName.isNotBlank() && nicknamePatter.matcher(nickName).matches()
    }

    override fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && namePatter.matcher(name).matches()
    }

    override fun isSurNameValid(surname: String): Boolean {
        return surname.isNotBlank() && namePatter.matcher(surname).matches()
    }

    override fun isBirthDayValid(birthDay: String): Boolean {
        return birthDay.isNotBlank() && datePattern.matcher(birthDay).matches()
    }
}
