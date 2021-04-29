package com.redmadrobot.domain.usecases.signup

import com.redmadrobot.domain.repository.RegisterRepository
import java.util.regex.Pattern

class RegisterUseCase(private val registerRepository: RegisterRepository) {

    fun update(nickname: String, email: String, password: String) {
        registerRepository.update(nickname, email, password)
    }

    fun login(name: String, surname: String, birthDay: String) = registerRepository.login(name, surname, birthDay)

    fun isEmailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String): Boolean {
        val pattern: Pattern
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,}$"
        pattern = Pattern.compile(passwordPattern)
        return pattern.matcher(password).matches()
    }

    fun isNicknameValid(nickName: String): Boolean {
        return nickName.isNotEmpty()
    }

    fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isSurNameValid(surname: String): Boolean {
        return surname.isNotEmpty()
    }

    fun isBirthDayValid(birthDay: String): Boolean {
        return birthDay.isNotEmpty()
    }
}
