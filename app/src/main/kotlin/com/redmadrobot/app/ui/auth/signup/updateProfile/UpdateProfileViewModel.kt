package com.redmadrobot.app.ui.auth.signup.updateProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.data.network.NetworkException
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.extensions.lifecycle.mapDistinct
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateProfileViewModel @Inject constructor(
    private val useCase: RegisterUseCase,
    private val validator: AuthValidator,
) : BaseViewModel() {
    private val liveState = MutableLiveData<UpdateProfileViewState>(UpdateProfileViewState())
    private var state: UpdateProfileViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val nicknameError = liveState.map { it.nickname.error }
    val nameError = liveState.map { it.name.error }
    val surnameError = liveState.map { it.surname.error }
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
            nickname = ValidAndError(
                isValid = validator.isNicknameValid(nickname),
                error = if (validator.isNicknameValid(nickname)) null else R.string.invalid_nickname
            )
        )
    }

    fun onNameEntered(name: String) {
        state = state.copy(
            name = ValidAndError(
                isValid = validator.isNameValid(name),
                error = if (validator.isNameValid(name)) null else R.string.invalid_name
            )
        )
    }

    fun onSurnameEntered(surname: String) {
        state = state.copy(
            surname = ValidAndError(
                isValid = validator.isSurNameValid(surname),
                error = if (validator.isSurNameValid(surname)) null else R.string.invalid_surname
            )
        )
    }

    fun onBirthDayEntered(birthDay: String) {
        state = state.copy(
            birthDay = ValidAndError(
                isValid = validator.isBirthDayValid(birthDay),
                error = if (validator.isBirthDayValid(birthDay)) null else R.string.invalid_birthday
            )
        )
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(UpdateProfileFragmentDirections.updateProfileFragmentPop()))
    }

    fun onRegisterClicked(nickname: String, name: String, surname: String, birthDay: String) {
        viewModelScope.launch {
            useCase.register(
                state.email ?: throw IllegalArgumentException("Email required"),
                state.password ?: throw IllegalArgumentException("Password required")
            ).onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }.catch { e ->
                processError(e)
            }.collect {
                updateProfile(
                    nickname = nickname,
                    name = name,
                    surname = surname,
                    birthDay = birthDay
                )
            }
        }
    }

    private fun processError(e: Throwable) {
        state = state.copy(screenState = ScreenState.ERROR)
        if (e !is NetworkException) throw e
        eventsQueue.offerEvent(EventError(e.message))
    }

    private suspend fun updateProfile(nickname: String, name: String, surname: String, birthDay: String) {
        useCase.updateProfile(
            nickname = nickname,
            firstName = name,
            lastName = surname,
            birthDay = birthDay
        ).onStart {
            state = state.copy(screenState = ScreenState.LOADING)
        }.catch { e ->
            processError(e)
        }.collect {
            eventsQueue.offerEvent(EventNavigateTo(UpdateProfileFragmentDirections.toDoneFragment()))
        }
    }
}
