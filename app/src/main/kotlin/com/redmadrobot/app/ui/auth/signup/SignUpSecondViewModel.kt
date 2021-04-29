package com.redmadrobot.app.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.data.repository.RegisterRepositoryImpl
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import kotlinx.coroutines.launch

class SignUpSecondViewModel : BaseViewModel() {
    private val _registerForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _registerForm

    // TODO: Inject
    private val registerRepository = RegisterRepositoryImpl()
    private val registerUseCase = RegisterUseCase(registerRepository)

    // TODO: Сделать _registerResult через EventQueue.RegisterSuccess RegisterFailed

    fun registerDataChanged(name: String, surname: String, birthDay: String) {
        when {
            !registerUseCase.isNameValid(name) -> {
                _registerForm.value = SignUpFormState(nameError = R.string.invalid_name)
            }

            !registerUseCase.isSurNameValid(surname) -> {
                _registerForm.value = SignUpFormState(surnameError = R.string.invalid_surname)
            }

            !registerUseCase.isBirthDayValid(birthDay) -> {
                _registerForm.value = SignUpFormState(birthDayError = R.string.invalid_birthday)
            }

            else -> {
                _registerForm.value = SignUpFormState(isDataValid = true)
            }
        }
    }

    fun login(name: String, surname: String, birthDay: String) {
        ioScope.launch {
            val result = registerUseCase.login(name, surname, birthDay)

            if (result.isSuccess) {
                // TODO: здесь заэммитить в _registerResult EventQueue.LoginSuccess
            } else {
                // TODO: здесь заэммитить в _registerResult EventQueue.LoginFailed
            }
        }
    }
}
