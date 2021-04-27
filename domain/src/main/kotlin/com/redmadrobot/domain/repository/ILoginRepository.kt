package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Optional
import com.redmadrobot.domain.entity.repository.login.LoggedInUser

/**
 * Интерфейс для управления логином
 */
interface ILoginRepository {
    fun logout()
    fun login(username: String, password: String): Optional<LoggedInUser>
}
