package com.redmadrobot.app.ui.auth.signup.register

import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(private val useCase: RegisterUseCase) : BaseViewModel() {
    var registerFormState = RegisterFormState()
        private set

    fun onRegisterDataChanged(email: String, password: String) {
        registerFormState = RegisterFormState()

        if (!useCase.isEmailValid(email)) {
            registerFormState.emailError = R.string.invalid_email
            registerFormState.isDataValid = false
        }
        if (!useCase.isPasswordValid(password)) {
            registerFormState.passwordError = R.string.invalid_password
            registerFormState.isDataValid = false
        }
        eventsQueue.offerEvent(EventRegisterFormStateChanged())
    }

    fun onRegisterClicked(email: String, password: String) {
        ioScope.launch {
            val result = useCase.register(email, password)

            if (result.isSuccess) {
                offerOnMain(EventRegisterSuccess())
            } else {
                offerOnMain(EventRegisterFailed())
            }
        }
    }
}
