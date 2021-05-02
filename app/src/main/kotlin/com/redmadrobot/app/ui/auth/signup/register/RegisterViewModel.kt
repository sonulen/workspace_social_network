package com.redmadrobot.app.ui.auth.signup.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(private val useCase: RegisterUseCase) : BaseViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val signUpFormState: LiveData<RegisterFormState> = _registerForm

    fun onRegisterDataChanged(email: String, password: String) {
        var isAllDataValid = true

        if (!useCase.isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
            isAllDataValid = false
        }

        if (!useCase.isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            isAllDataValid = false
        }

        if (isAllDataValid) {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    fun onRegisterClicked(email: String, password: String) {
        ioScope.launch {
            useCase.register(email, password)
        }
    }
}
