package com.redmadrobot.domain.usecases.signup

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.util.AuthValidator
import javax.inject.Inject

class ProfileUpdateUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: AuthValidator,
) {
    suspend fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
    ): Boolean = authRepository.updateProfile(
        nickname = nickname,
        firstName = firstName,
        lastName = lastName,
        birthDay = birthDay
    )

    fun isNicknameValid(nickName: String): Boolean = validator.isNicknameValid(nickName)
    fun isNameValid(name: String): Boolean = validator.isNameValid(name)
    fun isSurNameValid(surname: String): Boolean = validator.isSurNameValid(surname)
    fun isBirthDayValid(birthDay: String): Boolean = validator.isBirthDayValid(birthDay)
}
