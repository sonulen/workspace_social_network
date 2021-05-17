package com.redmadrobot.app.ui.auth.signup.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.extensions.lifecycle.mapDistinct
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val validator: AuthValidator) : BaseViewModel() {
    private val liveState = MutableLiveData<RegisterViewState>(RegisterViewState())
    private var state: RegisterViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val emailError = liveState.map { it.emailError }
    val passwordError = liveState.map { it.passwordError }
    val isGoNextButtonEnabled = liveState.mapDistinct { it.isEmailValid && it.isPasswordValid }

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

    fun onGoToLoginClicked() {
        eventsQueue.offerEvent(EventNavigateTo(RegisterFragmentDirections.toLoginFragment()))
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(RegisterFragmentDirections.registerFragmentPop()))
    }

    fun onGoNextClicked(email: String, password: String) {
        eventsQueue.offerEvent(
            EventNavigateTo(RegisterFragmentDirections.toUpdateProfileFragment(email, password))
        )
    }
}
