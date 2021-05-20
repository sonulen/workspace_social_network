package com.redmadrobot.domain.util

interface AuthValidator {

    fun isEmailValid(email: String): Boolean
    fun isPasswordValid(password: String): Boolean
    fun isNicknameValid(nickName: String): Boolean
    fun isNameValid(name: String): Boolean
    fun isSurNameValid(surname: String): Boolean
    fun isBirthDayValid(birthDay: String): Boolean
    fun getDataPattern(): String
}
