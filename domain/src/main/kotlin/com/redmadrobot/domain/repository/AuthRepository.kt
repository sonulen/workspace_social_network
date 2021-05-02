package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Result

interface AuthRepository {
    /**
     * Завершение сессии
     *
     */
    suspend fun logout(): Result<*>

    /**
     * Аутентификация пользователя
     *
     * @param email E-mail
     * @param password Пароль
     * @return Результат запроса
     */
    suspend fun login(email: String, password: String): Result<*>

    /**
     * Регистрация пользователя
     *
     * @param email E-mail
     * @param password Пароль
     * @return Результат запроса
     */
    suspend fun register(
        email: String,
        password: String,
    ): Result<*>

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
    ): Result<*>

    /**
     * Запрос на действующий access token
     * Если пользователь удачно логинился и есть refresh токен, в этом методе
     * Должен выполнять перезапрос access токена
     *
     * @return Результат с токеном. Токена может не сущетствовать.
     */
    suspend fun getCurrentAccessToken(): Result<*>
}
