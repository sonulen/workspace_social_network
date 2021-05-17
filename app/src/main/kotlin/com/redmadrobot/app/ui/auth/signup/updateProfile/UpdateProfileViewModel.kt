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
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.extensions.lifecycle.mapDistinct
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
        state = state.copy(
            screenState = ScreenState.LOADING
        )

        viewModelScope.launch {
            state = if (
                register() &&
                updateProfile(
                    nickname = nickname,
                    name = name,
                    surname = surname,
                    birthDay = birthDay
                )
            ) {
                state.copy(
                    screenState = ScreenState.CONTENT
                )
            } else {
                state.copy(
                    screenState = ScreenState.ERROR
                )
            }
        }
    }

    private suspend fun register(): Boolean {
        with(state) {
            if (email != null && password != null) {
                val result = useCase.register(email, password)

                if (!result) {
                    offerOnMain(EventError("Зарегестрироваться не удалось"))
                    return false
                }
            }
        }
        return true
    }

    private suspend fun updateProfile(nickname: String, name: String, surname: String, birthDay: String): Boolean {
        val result = useCase.updateProfile(
            nickname = nickname,
            firstName = name,
            lastName = surname,
            birthDay = birthDay
        )

        if (result) {
            offerOnMain(EventNavigateTo(UpdateProfileFragmentDirections.toDoneFragment()))
        } else {
            offerOnMain(EventError("Обновить профиль не удалось"))
            return false
        }

        return true
    }
}
