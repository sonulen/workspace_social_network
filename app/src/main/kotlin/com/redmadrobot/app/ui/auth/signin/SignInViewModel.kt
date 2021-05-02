package com.redmadrobot.app.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.login.LoginUseCase
import kotlinx.coroutines.launch

class SignInViewModel constructor(private val useCase: LoginUseCase) : BaseViewModel() {

    private val _loginForm = MutableLiveData<SignInFormState>()
    val signInFormState: LiveData<SignInFormState> = _loginForm

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
                _loginForm.value = SignInFormState(emailError = R.string.invalid_email)
            }

            !useCase.isPasswordValid(password) -> {
                _loginForm.value = SignInFormState(passwordError = R.string.invalid_password)
            }

            else -> {
                _loginForm.value = SignInFormState(isDataValid = true)
            }
        }
    }
}
