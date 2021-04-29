package com.redmadrobot.app.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.data.repository.LoginRepositoryImpl
import com.redmadrobot.domain.usecases.login.LoginUseCase
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {
    private val _loginForm = MutableLiveData<SignInFormState>()
    val signInFormState: LiveData<SignInFormState> = _loginForm

    // TODO: Сделать _loginResult через EventQueue.LoginSuccess LoginFailed

    // TODO: Inject
    private val loginRepository = LoginRepositoryImpl()
    private val loginUseCase = LoginUseCase(loginRepository)

    fun login(email: String, password: String) {
        ioScope.launch {
            val result = loginUseCase.login(email, password)

            if (result.isSuccess) {
                // TODO: здесь заэммитить в _loginResult EventQueue.LoginSuccess
            } else {
                // TODO: здесь заэммитить в _loginResult EventQueue.LoginFailed
            }
        }
    }

    fun onLoginDataChanged(email: String, password: String) {
        when {
            !loginUseCase.isEmailValid(email) -> {
                _loginForm.value = SignInFormState(emailError = R.string.invalid_email)
            }

            !loginUseCase.isPasswordValid(password) -> {
                _loginForm.value = SignInFormState(passwordError = R.string.invalid_password)
            }

            else -> {
                _loginForm.value = SignInFormState(isDataValid = true)
            }
        }
    }
}
