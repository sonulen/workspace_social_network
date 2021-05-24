package com.redmadrobot.app.ui.workspace.profileEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.auth.signup.updateProfile.ValidAndError
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.events.EventShowDatePickerDialog
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.ui.workspace.profile.UserDataProfileViewState
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.repository.UserDataRepository
import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.extensions.lifecycle.mapDistinct
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileEditViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val validator: AuthValidator,
) : BaseViewModel() {
    private val liveState = MutableLiveData<ProfileEditViewState>(ProfileEditViewState())
    private var state: ProfileEditViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val isSaveButtonEnable = liveState.mapDistinct {
        it.name.isValid && it.birthDay.isValid && it.nickname.isValid &&
            it.surname.isValid && it.userData != it.userDataForm
    }
    val userData = liveState.mapDistinct { it.userData }
    val nicknameError = liveState.map { it.nickname.error }
    val nameError = liveState.map { it.name.error }
    val surnameError = liveState.map { it.surname.error }
    val birthDayError = liveState.map { it.birthDay.error }

    init {
        viewModelScope.launch {
            userDataRepository.getUserProfileDataFlow()
                .onStart {
                    state = state.copy(screenState = ScreenState.LOADING)
                }.catch { e ->
                    processError(e)
                }.collect {
                    state = state.copy(
                        screenState = ScreenState.CONTENT,
                        userData = UserDataProfileViewState(
                            it.nickname,
                            it.firstName,
                            it.lastName,
                            it.birthDay
                        ),
                        userDataForm = UserDataProfileViewState(
                            it.nickname,
                            it.firstName,
                            it.lastName,
                            it.birthDay
                        ),
                    )
                }
        }
    }

    private fun processError(e: Throwable) {
        state = state.copy(screenState = ScreenState.ERROR)
        if (e !is NetworkException) throw e
        eventsQueue.offerEvent(EventError(e.message))
    }

    fun onNicknameEntered(nickname: String) {
        val isValid = validator.isNicknameValid(nickname)

        state = state.copy(
            nickname = ValidAndError(
                isValid = isValid,
                error = if (isValid) null else R.string.invalid_nickname
            ),
            userDataForm = state.userDataForm.copy(nickname = nickname)
        )
    }

    fun onNameEntered(name: String) {
        val isValid = validator.isNameValid(name)

        state = state.copy(
            name = ValidAndError(
                isValid = isValid,
                error = if (isValid) null else R.string.invalid_name
            ),
            userDataForm = state.userDataForm.copy(name = name)
        )
    }

    fun onSurnameEntered(surname: String) {
        val isValid = validator.isSurNameValid(surname)
        state = state.copy(
            surname = ValidAndError(
                isValid = isValid,
                error = if (isValid) null else R.string.invalid_surname
            ),
            userDataForm = state.userDataForm.copy(surname = surname)
        )
    }

    fun onBirthDayEntered(birthDay: String) {
        val isValid = validator.isBirthDayValid(birthDay)
        state = state.copy(
            birthDay = ValidAndError(
                isValid = isValid,
                error = if (isValid) null else R.string.invalid_birthday
            ),
            userDataForm = state.userDataForm.copy(birthDay = birthDay)
        )
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            userDataRepository.updateUserProfileData(
                state.userDataForm.nickname,
                state.userDataForm.name,
                state.userDataForm.surname,
                state.userDataForm.birthDay,
                null
            ).onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }.catch { e ->
                processError(e)
            }.collect {
                state = state.copy(screenState = ScreenState.CONTENT)
            }
        }
    }

    fun onBackClicked() {
        eventsQueue.offerEvent(EventNavigateTo(ProfileEditFragmentDirections.profileEditFragmentPop()))
    }

    fun onShowDatePickerClicked() {
        eventsQueue.offerEvent(EventShowDatePickerDialog())
    }
}
