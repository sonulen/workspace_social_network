package com.redmadrobot.app.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.data.repository.RegisterRepository
import com.redmadrobot.domain.entity.repository.signup.RegisterFormState
import com.redmadrobot.domain.usecases.signup.RegisterUseCase

class SignUpSecondViewModel : BaseViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    // TODO: Inject
    private val registerRepository = RegisterRepository()
    private val registerUseCase = RegisterUseCase(registerRepository)

    // TODO: Сделать _registerResult через EventQueue.RegisterSuccess RegisterFailed

    fun registerDataChanged(name: String, surname: String, birthDay: String) {
        when {
            !registerUseCase.isNameValid(name) -> {
                _registerForm.value = RegisterFormState(nameError = R.string.invalid_name)
            }

            !registerUseCase.isSurNameValid(surname) -> {
                _registerForm.value = RegisterFormState(surnameError = R.string.invalid_surname)
            }

            !registerUseCase.isBirthDayValid(birthDay) -> {
                _registerForm.value = RegisterFormState(birthDayError = R.string.invalid_birthday)
            }

            else -> {
                _registerForm.value = RegisterFormState(isDataValid = true)
            }
        }
    }

    fun login(name: String, surname: String, birthDay: String) {
        val result = registerUseCase.login(name, surname, birthDay)

        if (result.isSuccess) {
            // TODO: здесь заэммитить в _registerResult EventQueue.LoginSuccess
        } else {
            // TODO: здесь заэммитить в _registerResult EventQueue.LoginFailed
        }
    }
}
