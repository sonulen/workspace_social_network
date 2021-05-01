package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.User
import com.redmadrobot.domain.repository.RegisterRepository
import kotlinx.coroutines.delay
import java.util.*

class RegisterRepositoryImpl : RegisterRepository {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    private var email: String = ""

    init {
        user = null
    }

    override fun update(nickname: String, email: String, password: String) {
        this.email = email
    }

    override suspend fun register(name: String, surname: String, birthDay: String): Result<*> {
        // TODO: Поход в сеть на регистрацию
        delay(timeMillis = 5000)
        val result = Result.Success(User(UUID.randomUUID().toString(), email))
        setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(user: User) {
        this.user = user
    }
}
