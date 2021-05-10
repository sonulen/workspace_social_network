package com.redmadrobot.app.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.domain.usecases.login.LoginUseCase
import com.redmadrobot.extensions.lifecycle.mapDistinct
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val useCase: LoginUseCase) : BaseViewModel() {
    private val liveState = MutableLiveData<LoginViewState>(LoginViewState())
    private var state: LoginViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val emailError = liveState.map { it.emailError }
    val passwordError = liveState.map { it.passwordError }
    val isLoginButtonEnabled = liveState.mapDistinct { it.isEmailValid && it.isPasswordValid }

    fun onLoginClicked(email: String, password: String) {
        state = state.copy(
            screenState = ScreenState.LOADING
        )
        viewModelScope.launch {
            val result = useCase.login(email, password)

            if (result) {
                state = state.copy(
                    screenState = ScreenState.CONTENT
                )
                offerOnMain(EventNavigateTo(LoginFragmentDirections.toDoneFragment()))
            } else {
                state = state.copy(
                    screenState = ScreenState.ERROR
                )
                offerOnMain(EventError("Что-то пошло не так"))
            }
        }
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(LoginFragmentDirections.loginFragmentPop()))
    }

    fun onGoToRegisterClicked() {
        eventsQueue.offerEvent(EventNavigateTo(LoginFragmentDirections.toRegisterFragment()))
    }

    fun onPasswordEntered(password: String) {
        state = state.copy(
            isPasswordValid = useCase.isPasswordValid(password),
            passwordError = if (useCase.isPasswordValid(password)) null else R.string.invalid_password
        )
    }

    fun onEmailEntered(email: String) {
        state = state.copy(
            isEmailValid = useCase.isEmailValid(email),
            emailError = if (useCase.isEmailValid(email)) null else R.string.invalid_email
        )
    }
}
