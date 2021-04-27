package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Optional
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

    override fun login(username: String, password: String): Optional<LoggedInUser> {
        // TODO: Поход в сеть на логин
        val result = Optional.Success(LoggedInUser(UUID.randomUUID().toString(), username))
        setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        user = loggedInUser
    }
}
