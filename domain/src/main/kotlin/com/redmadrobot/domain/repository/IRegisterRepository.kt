package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.LoggedInUser

/**
 * Интерфейс для управления регистрацией
 */
interface IRegisterRepository {
    fun update(nickname: String, email: String, password: String)
    fun login(name: String, surname: String, birthDay: String): Result<LoggedInUser>
}
