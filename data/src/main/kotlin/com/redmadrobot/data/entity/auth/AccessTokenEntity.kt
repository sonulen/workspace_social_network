package com.redmadrobot.data.entity.auth

import java.util.*

/**
 * Сущность хранения access токена
 *
 * @property token Сам токен
 */
class AccessTokenEntity(val token: String) {
    private val lifeTimeInHour: Int = 1
    private val expirationDate = GregorianCalendar().add(Calendar.HOUR_OF_DAY, lifeTimeInHour)

    fun isExpired(): Boolean {
        return GregorianCalendar().after(expirationDate)
    }
}
