package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.User

/**
 * Интерфейс для управления логином
 */
interface LoginRepository {
    fun logout()
    fun login(email: String, password: String): Result<User>
}
