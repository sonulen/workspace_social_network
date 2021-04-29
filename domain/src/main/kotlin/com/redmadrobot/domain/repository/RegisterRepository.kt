package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Result

/**
 * Интерфейс для управления регистрацией
 */
interface RegisterRepository {
    fun update(nickname: String, email: String, password: String)
    suspend fun register(name: String, surname: String, birthDay: String): Result<*>
}
