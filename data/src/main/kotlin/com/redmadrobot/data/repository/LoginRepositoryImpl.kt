package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.User
import com.redmadrobot.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import java.util.*

class LoginRepositoryImpl : LoginRepository {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    override fun logout() {
        user = null
    }

    override suspend fun login(email: String, password: String): Result<*> {
        // TODO: Поход в сеть на логин
        delay(5000)
        val result = Result.Success(User(UUID.randomUUID().toString(), email))
        setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(user: User) {
        this.user = user
    }
}
