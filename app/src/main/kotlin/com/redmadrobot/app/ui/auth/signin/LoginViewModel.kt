package com.redmadrobot.app.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.login.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel constructor(private val useCase: LoginUseCase) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun onLoginClicked(email: String, password: String) {
        ioScope.launch {
            val result = useCase.login(email, password)

            if (result.isSuccess) {
                // TODO: здесь заэммитить в _loginResult EventQueue.LoginSuccess
            } else {
                // TODO: здесь заэммитить в _loginResult EventQueue.LoginFailed
            }
        }
    }

    fun onLoginDataChanged(email: String, password: String) {
        when {
            !useCase.isEmailValid(email) -> {
                _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            }

            !useCase.isPasswordValid(password) -> {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            }

            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }
}
