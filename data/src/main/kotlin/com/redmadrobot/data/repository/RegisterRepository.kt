package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Optional
import com.redmadrobot.domain.entity.repository.login.LoggedInUser
import com.redmadrobot.domain.repository.IRegisterRepository
import java.util.*

class RegisterRepository : IRegisterRepository {
    var user: LoggedInUser? = null
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

    override fun login(name: String, surname: String, birthDay: String): Optional<LoggedInUser> {
        // TODO: Поход в сеть на регистрацию
        val result = Optional.Success(LoggedInUser(UUID.randomUUID().toString(), email))
        setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        user = loggedInUser
    }
}
