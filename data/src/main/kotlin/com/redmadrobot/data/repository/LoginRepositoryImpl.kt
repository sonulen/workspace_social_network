package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.User
import com.redmadrobot.domain.repository.LoginRepository
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

    override fun login(email: String, password: String): Result<User> {
        // TODO: Поход в сеть на логин
        val result = Result.Success(User(UUID.randomUUID().toString(), email))
        setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(user: User) {
        this.user = user
    }
}
