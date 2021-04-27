package com.redmadrobot.app.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.data.repository.LoginRepository
import com.redmadrobot.domain.entity.repository.Optional
import com.redmadrobot.domain.entity.repository.login.LoginFormState
import com.redmadrobot.domain.usecases.login.LoginUseCase

class SignUpFirstViewModel : BaseViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    // TODO: Inject
    private val loginRepository = LoginRepository()
    private val loginUseCase = LoginUseCase(loginRepository)

    fun login(username: String, password: String) {
        val result = loginUseCase.login(username, password)

        if (result is Optional.Success<*>) {
            // TODO: здесь заэммитить в _loginResult EventQueue.LoginSuccess
        } else {
            // TODO: здесь заэммитить в _loginResult EventQueue.LoginFailed
        }
    }

    fun loginDataChanged(email: String, password: String) {
        when {
            !loginUseCase.isEmailValid(email) -> {
                _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            }

            !loginUseCase.isPasswordValid(password) -> {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            }

            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }
}
