package com.redmadrobot.domain.usecases.login

import com.redmadrobot.domain.repository.ILoginRepository
import java.util.regex.Pattern

class LoginUseCase(private val loginRepository: ILoginRepository) {

    fun login(email: String, password: String) = loginRepository.login(email, password)

    fun isEmailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String): Boolean {
        val pattern: Pattern
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,}$"
        pattern = Pattern.compile(passwordPattern)
        return pattern.matcher(password).matches()
    }
}
