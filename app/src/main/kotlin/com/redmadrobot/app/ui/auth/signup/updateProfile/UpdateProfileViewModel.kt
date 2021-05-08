package com.redmadrobot.app.ui.auth.signup.updateProfile

import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.signup.ProfileUpdateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateProfileViewModel @Inject constructor(private val useCase: ProfileUpdateUseCase) : BaseViewModel() {
    var updateProfileFormState = UpdateProfileFormState()
        private set

    fun onRegisterDataChanged(nickname: String, name: String, surname: String, birthDay: String) {
        updateProfileFormState = UpdateProfileFormState()

        if (!useCase.isNicknameValid(nickname)) {
            updateProfileFormState.nicknameError = R.string.invalid_nickname
            updateProfileFormState.isDataValid = false
        }

        if (!useCase.isNameValid(name)) {
            updateProfileFormState.nameError = R.string.invalid_name
            updateProfileFormState.isDataValid = false
        }

        if (!useCase.isSurNameValid(surname)) {
            updateProfileFormState.surnameError = R.string.invalid_surname
            updateProfileFormState.isDataValid = false
        }
        if (!useCase.isBirthDayValid(birthDay)) {
            updateProfileFormState.birthDayError = R.string.invalid_birthday
            updateProfileFormState.isDataValid = false
        }

        eventsQueue.offerEvent(EventUpdateProfileFormStateChanged())
    }

    fun onUpdateProfileClicked(nickname: String, name: String, surname: String, birthDay: String) {
        viewModelScope.launch {
            val result = useCase.updateProfile(
                nickname = nickname,
                firstName = name,
                lastName = surname,
                birthDay = birthDay
            )

            if (result) {
                offerOnMain(EventUpdateProfileSuccess())
            } else {
                offerOnMain(EventUpdateProfileFailed())
            }
        }
    }
}
