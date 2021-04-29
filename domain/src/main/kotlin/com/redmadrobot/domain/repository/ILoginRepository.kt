package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.LoggedInUser

/**
 * Интерфейс для управления логином
 */
interface ILoginRepository {
    fun logout()
    fun login(email: String, password: String): Result<LoggedInUser>
}
