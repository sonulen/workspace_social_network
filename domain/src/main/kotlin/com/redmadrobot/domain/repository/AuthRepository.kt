package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.entity.repository.UserProfileData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * Завершение сессии
     */
    fun logout(): Flow<Unit>

    /**
     * Аутентификация пользователя
     *
     * @param email E-mail
     * @param password Пароль
     * @return Пара access и refresh токен
     */
    fun login(email: String, password: String): Flow<Tokens>

    /**
     * Запрос новых токенов через refresh
     *
     * Refresh токен должен уже где то хранится
     *
     * @return Пара access и refresh токен
     */
    fun refresh(): Flow<Tokens>

    /**
     * Регистрация пользователя
     *
     * @param email E-mail
     * @param password Пароль
     * @return Результат запроса
     */
    fun register(email: String, password: String): Flow<Tokens>

    /**
     * Обновление данных профиля пользователя
     *
     * @param nickname Никнейм
     * @param firstName Имя
     * @param lastName Фамилия
     * @param birthDay Дата рождения
     * @return Результат запроса
     */
    fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
    ): Flow<UserProfileData>
}
