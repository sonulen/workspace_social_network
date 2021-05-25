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
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.extensions.lifecycle.mapDistinct
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: AuthValidator,
) : BaseViewModel() {
    private val liveState = MutableLiveData<LoginViewState>(LoginViewState())
    private var state: LoginViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val emailError = liveState.map { it.emailError }
    val passwordError = liveState.map { it.passwordError }
    val isLoginButtonEnabled = liveState.mapDistinct { it.isEmailValid && it.isPasswordValid }

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password)
                .onStart {
                    state = state.copy(screenState = ScreenState.LOADING)
                }.catch { e ->
                    state = state.copy(screenState = ScreenState.ERROR)
                    if (e !is NetworkException) throw e
                    eventsQueue.offerEvent(EventError(e.message))
                }.collect {
                    state = state.copy(screenState = ScreenState.CONTENT)
                    eventsQueue.offerEvent(EventNavigateTo(LoginFragmentDirections.toDoneFragment()))
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
            isPasswordValid = validator.isPasswordValid(password),
            passwordError = if (validator.isPasswordValid(password)) null else R.string.invalid_password
        )
    }

    fun onEmailEntered(email: String) {
        state = state.copy(
            isEmailValid = validator.isEmailValid(email),
            emailError = if (validator.isEmailValid(email)) null else R.string.invalid_email
        )
    }
}
