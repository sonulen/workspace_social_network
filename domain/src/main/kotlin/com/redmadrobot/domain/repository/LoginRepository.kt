package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Result

/**
 * Интерфейс для управления логином
 */
interface LoginRepository {
    fun logout()
    suspend fun login(email: String, password: String): Result<*>
}
