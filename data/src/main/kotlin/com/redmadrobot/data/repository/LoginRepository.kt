package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.entity.repository.login.LoggedInUser
import com.redmadrobot.domain.repository.ILoginRepository
import java.util.*

class LoginRepository : ILoginRepository {
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    override fun logout() {
        user = null
    }

    override fun login(email: String, password: String): Result<LoggedInUser> {
        // TODO: Поход в сеть на логин
        val result = Result.Success(LoggedInUser(UUID.randomUUID().toString(), email))
        setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        user = loggedInUser
    }
}
