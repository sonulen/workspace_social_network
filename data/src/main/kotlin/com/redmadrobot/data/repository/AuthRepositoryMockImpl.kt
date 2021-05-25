package com.redmadrobot.data.repository

import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryMockImpl : AuthRepository {
    private val stubTokens = Tokens("access", "refresh")
    private val stubUserProfileData = UserProfileData(
        id = "1",
        firstName = "MockName",
        lastName = "MockLastName",
        nickname = "MockNickname",
        avatarUrl = null,
        birthDay = "2001-01-01"
    )

    /**
     * /see [AuthRepository.logout]
     */
    override fun logout(): Flow<Unit> = flow {
        delay(timeMillis = 5_000)
        emit(Unit)
    }

    /**
     * /see [AuthRepository.login]
     */
    override fun login(email: String, password: String): Flow<Tokens> = flow {
        delay(timeMillis = 5_000)
        emit(stubTokens)
    }

    /**
     * Запрос новых токенов через refresh
     *
     * Refresh токен должен уже где то хранится
     *
     * @return Пара access и refresh токен
     */
    override fun refresh(): Flow<Tokens> = flow {
        delay(timeMillis = 5_000)
        emit(stubTokens)
    }

    /**
     * /see [AuthRepository.register]
     */
    override fun register(email: String, password: String): Flow<Tokens> = flow {
        delay(timeMillis = 5_000)
        emit(stubTokens)
    }

    /**
     * Обновление данных профиля пользователя
     *
     * @param nickname Никнейм
     * @param firstName Имя
     * @param lastName Фамилия
     * @param birthDay Дата рождения
     * @return Результат запроса
     */
    override fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
    ): Flow<UserProfileData> = flow {
        delay(timeMillis = 5_000)
        emit(stubUserProfileData)
    }
}
