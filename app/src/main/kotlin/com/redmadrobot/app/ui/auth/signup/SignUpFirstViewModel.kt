package com.redmadrobot.app.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.data.repository.RegisterRepository
import com.redmadrobot.domain.entity.repository.signup.RegisterFormState
import com.redmadrobot.domain.usecases.signup.RegisterUseCase

class SignUpFirstViewModel : BaseViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    // TODO: Inject
    private val registerRepository = RegisterRepository()
    private val registerUseCase = RegisterUseCase(registerRepository)

    fun onRegisterDataChanged(nickname: String, email: String, password: String) {
        when {
            !registerUseCase.isEmailValid(email) -> {
                _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
            }

            !registerUseCase.isNicknameValid(nickname) -> {
                _registerForm.value = RegisterFormState(nicknameError = R.string.invalid_nickname)
            }

            !registerUseCase.isPasswordValid(password) -> {
                _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            }

            else -> {
                _registerForm.value = RegisterFormState(isDataValid = true)
            }
        }
    }

    fun update(nickname: String, email: String, password: String) {
        registerUseCase.update(nickname, email, password)
    }
}
