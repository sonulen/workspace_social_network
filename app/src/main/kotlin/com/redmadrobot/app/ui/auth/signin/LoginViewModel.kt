package com.redmadrobot.app.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.ErrorMessage
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.utils.InputField
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.extensions.lifecycle.mapDistinct
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: AuthValidator,
) : BaseViewModel() {
    private val liveState = MutableLiveData(LoginViewState())
    private var state: LoginViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val email = liveState.mapDistinct { it.email.value }
    val emailError = liveState.map { it.email.error }
    val password = liveState.mapDistinct { it.password.value }
    val passwordError = liveState.map { it.password.error }
    val isLoginButtonEnabled = liveState.mapDistinct { it.email.isValid && it.password.isValid }

    fun onLoginClicked() {
        authRepository.login(state.email.value, state.password.value)
            .onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .catch { e ->
                state = state.copy(screenState = ScreenState.ERROR)
                if (e is NetworkException) {
                    eventsQueue.offerEvent(EventError(ErrorMessage.Text(e.message)))
                }
            }
            .onEach {
                state = state.copy(screenState = ScreenState.CONTENT)
                eventsQueue.offerEvent(EventNavigateTo(LoginFragmentDirections.toDoneFragment()))
            }
            .launchIn(viewModelScope)
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(LoginFragmentDirections.loginFragmentPop()))
    }

    fun onGoToRegisterClicked() {
        eventsQueue.offerEvent(EventNavigateTo(LoginFragmentDirections.toRegisterFragment()))
    }

    fun onPasswordEntered(password: String) {
        state = state.copy(
            password = InputField(
                value = password,
                isValid = validator.isPasswordValid(password),
                error = if (validator.isPasswordValid(password)) null else R.string.invalid_password
            )
        )
    }

    fun onEmailEntered(email: String) {
        state = state.copy(
            email = InputField(
                value = email,
                isValid = validator.isEmailValid(email),
                error = if (validator.isEmailValid(email)) null else R.string.invalid_email
            )
        )
    }
}
