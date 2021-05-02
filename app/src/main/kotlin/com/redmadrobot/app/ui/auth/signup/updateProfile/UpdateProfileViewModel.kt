package com.redmadrobot.app.ui.auth.signup.updateProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.usecases.signup.ProfileUpdateUseCase
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val useCase: ProfileUpdateUseCase) : BaseViewModel() {
    private val _registerForm = MutableLiveData<UpdateProfileFormState>()
    val signUpFormState: LiveData<UpdateProfileFormState> = _registerForm

    fun registerDataChanged(nickname: String, name: String, surname: String, birthDay: String) {
        var isAllDataValid = true

        if (!useCase.isNicknameValid(nickname)) {
            _registerForm.value = UpdateProfileFormState(nameError = R.string.invalid_name)
            isAllDataValid = false
        }

        if (!useCase.isNameValid(name)) {
            _registerForm.value = UpdateProfileFormState(nameError = R.string.invalid_name)
            isAllDataValid = false
        }

        if (!useCase.isSurNameValid(surname)) {
            _registerForm.value = UpdateProfileFormState(surnameError = R.string.invalid_surname)
            isAllDataValid = false
        }
        if (!useCase.isBirthDayValid(birthDay)) {
            _registerForm.value = UpdateProfileFormState(birthDayError = R.string.invalid_birthday)
            isAllDataValid = false
        }

        if (isAllDataValid) {
            _registerForm.value = UpdateProfileFormState(isDataValid = true)
        }
    }

    fun onUpdateProfileClicked(nickname: String, name: String, surname: String, birthDay: String) {
        ioScope.launch {
            val result = useCase.updateProfile("nickname", name, surname, birthDay)

            if (result.isSuccess) {
                // TODO: здесь заэммитить в _registerResult EventQueue.LoginSuccess
            } else {
                // TODO: здесь заэммитить в _registerResult EventQueue.LoginFailed
            }
        }
    }
}
