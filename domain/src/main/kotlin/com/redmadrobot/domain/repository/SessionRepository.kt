package com.redmadrobot.domain.repository

import com.redmadrobot.domain.entity.repository.Tokens

interface SessionRepository {
    /**
     * Сохранение текущей сессии
     *
     * refresh токен должен переживать приложение и удаляться только в clear
     *
     * @param tokens Access и refresh токены
     */
    fun saveSession(tokens: Tokens)

    /**
     * Getter на действующий access token
     * Access токен не должен переживать приложение
     *
     * @return Результат с токеном. Токена может не сущетствовать.
     */
    fun getAccessToken(): String?

    /**
     * Getter на refresh токен
     *
     * Рефреш токен должен переживать приложение
     *
     * @return Строка с refresh токеном. Его может не быть
     */
    fun getRefreshToken(): String?

    /**
     * Активна ли сессия, т.е. есть ли refresh токен
     * Проверка не на access токен потому что даже если он истек его можно будет обновить через refresh
     *
     * @return true - если есть refresh
     */
    fun sessionExists(): Boolean

    /**
     * Удаление текущей сессии
     * Удалится должен и access и refresh токен
     */
    fun clear()
}
