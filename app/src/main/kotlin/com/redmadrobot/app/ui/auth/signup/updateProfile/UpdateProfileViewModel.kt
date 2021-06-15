package com.redmadrobot.app.ui.auth.signup.updateProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.events.EventShowDatePickerDialog
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

class UpdateProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: AuthValidator,
) : BaseViewModel() {
    private val liveState = MutableLiveData(UpdateProfileViewState())
    private var state: UpdateProfileViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val nickname = liveState.mapDistinct { it.nickname.value }
    val nicknameError = liveState.map { it.nickname.error }
    val name = liveState.mapDistinct { it.name.value }
    val nameError = liveState.map { it.name.error }
    val surname = liveState.mapDistinct { it.surname.value }
    val surnameError = liveState.map { it.surname.error }
    val birthDay = liveState.mapDistinct { it.birthDay.value }
    val birthDayError = liveState.map { it.birthDay.error }
    val isRegisterButtonEnabled = liveState.mapDistinct {
        it.nickname.isValid && it.name.isValid && it.surname.isValid &&
            it.birthDay.isValid && it.email != null && it.password != null
    }

    fun onEmailAndPasswordReceived(email: String, password: String) {
        state = state.copy(
            email = email,
            password = password
        )
    }

    fun onNicknameEntered(nickname: String) {
        state = state.copy(
            nickname = InputField(
                value = nickname,
                isValid = validator.isNicknameValid(nickname),
                error = if (validator.isNicknameValid(nickname)) null else R.string.invalid_nickname
            )
        )
    }

    fun onNameEntered(name: String) {
        state = state.copy(
            name = InputField(
                value = name,
                isValid = validator.isNameValid(name),
                error = if (validator.isNameValid(name)) null else R.string.invalid_name
            )
        )
    }

    fun onSurnameEntered(surname: String) {
        state = state.copy(
            surname = InputField(
                value = surname,
                isValid = validator.isSurNameValid(surname),
                error = if (validator.isSurNameValid(surname)) null else R.string.invalid_surname
            )
        )
    }

    fun onBirthDayEntered(birthDay: String) {
        state = state.copy(
            birthDay = InputField(
                value = birthDay,
                isValid = validator.isBirthDayValid(birthDay),
                error = if (validator.isBirthDayValid(birthDay)) null else R.string.invalid_birthday
            )
        )
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(UpdateProfileFragmentDirections.updateProfileFragmentPop()))
    }

    fun onRegisterClicked() {
        authRepository.register(
            requireNotNull(state.email) { "Email required" },
            requireNotNull(state.password) { "Password required" }
        )
            .onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .catch { e ->
                processError(e)
            }
            .onEach {
                updateProfile()
            }
            .launchIn(viewModelScope)
    }

    private fun processError(e: Throwable) {
        state = state.copy(screenState = ScreenState.ERROR)
        if (e is NetworkException) {
            eventsQueue.offerEvent(EventError(e.message))
        }
    }

    private fun updateProfile() {
        authRepository.updateProfile(
            nickname = state.nickname.value,
            firstName = state.name.value,
            lastName = state.surname.value,
            birthDay = state.birthDay.value,
        )
            .onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .catch { e ->
                processError(e)
            }
            .onEach {
                state = state.copy(screenState = ScreenState.CONTENT)
                eventsQueue.offerEvent(EventNavigateTo(UpdateProfileFragmentDirections.toDoneFragment()))
            }
            .launchIn(viewModelScope)
    }

    fun onShowDatePickerClicked() {
        eventsQueue.offerEvent(EventShowDatePickerDialog())
    }
}
