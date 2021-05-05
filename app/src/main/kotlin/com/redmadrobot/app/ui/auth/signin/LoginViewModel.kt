package com.redmadrobot.app.ui.auth.signin

import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.login.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val useCase: LoginUseCase) : BaseViewModel() {
    var loginFormState = LoginFormState()
        private set

    fun onLoginClicked(email: String, password: String) {
        ioScope.launch {
            val result = useCase.login(email, password)

            if (result.isSuccess) {
                offerOnMain(EventLoginSuccess())
            } else {
                offerOnMain(EventLoginFailed())
            }
        }
    }

    fun onLoginDataChanged(email: String, password: String) {
        loginFormState = LoginFormState()

        if (!useCase.isEmailValid(email)) {
            loginFormState.emailError = R.string.invalid_email
            loginFormState.isDataValid = false
        }

        if (!useCase.isPasswordValid(password)) {
            loginFormState.passwordError = R.string.invalid_password
            loginFormState.isDataValid = false
        }

        eventsQueue.offerEvent(EventLoginFormStateChanged())
    }
}
