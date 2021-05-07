package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Tokens

interface AuthRepository {

    /**
     * Завершение сессии
     */
    suspend fun logout()

    /**
     * Аутентификация пользователя
     *
     * @param email E-mail
     * @param password Пароль
     * @return Пара access и refresh токен
     */
    suspend fun login(email: String, password: String): Tokens

    /**
     * Регистрация пользователя
     *
     * @param email E-mail
     * @param password Пароль
     * @return Результат запроса
     */
    suspend fun register(email: String, password: String): Tokens

    /**
     * Обновление данных профиля пользователя
     *
     * @param nickname Никнейм
     * @param firstName Имя
     * @param lastName Фамилия
     * @param avatarUrl Ссылка на аватар
     * @param birthDay Дата рождения
     * @return Результат запроса
     */
    suspend fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String? = null,
    ): Boolean
}
