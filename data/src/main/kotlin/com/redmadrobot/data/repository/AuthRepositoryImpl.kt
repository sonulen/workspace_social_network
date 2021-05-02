package com.redmadrobot.data.repository

import android.content.SharedPreferences
import com.redmadrobot.data.entity.api.NetworkEntityToken
import com.redmadrobot.data.entity.auth.AccessTokenEntity
import com.redmadrobot.domain.entity.repository.Result
import com.redmadrobot.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.IOException

class AuthRepositoryImpl constructor(
    private val sharedPrefs: SharedPreferences,
) : AuthRepository {
    var accessToken: AccessTokenEntity? = null

    companion object {
        private const val AUTH_REFRESH_TOKEN = "AUTH_REFRESH_TOKEN"
    }

    /**
     * /see [AuthRepository.logout]
     */
    override suspend fun logout(): Result<*> {
        removeToken()
        return Result.Success(true)
    }

    /**
     * /see [AuthRepository.login]
     */
    override suspend fun login(email: String, password: String): Result<*> {
        delay(timeMillis = 5000)
        val result = Result.Success(NetworkEntityToken("1234", "56789"))

        if (result.isSuccess) {
            save(result.data)
            return Result.Success(accessToken!!)
        }

        Timber.d("Аутентификация провалена")
        return Result.Error(IOException("Аутентификация провалена"))
    }

    /**
     * /see [AuthRepository.register]
     */
    override suspend fun register(
        email: String,
        password: String,
    ): Result<*> {
        delay(timeMillis = 5000)
        val result = Result.Success(NetworkEntityToken("1234", "56789"))

        if (result.isSuccess) {
            save(result.data)
            return Result.Success(accessToken!!.token)
        }

        Timber.d("Регистрация провалена")
        return Result.Error(IOException("Регистрация провалена"))
    }

    /**
     * /see [AuthRepository.updateProfile]
     */
    override suspend fun updateProfile(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String?,
    ): Result<*> {
        delay(timeMillis = 5000)
        return Result.Success("Профиль обновлен")
    }

    /**
     * /see [AuthRepository.getCurrentAccessToken]
     */
    override suspend fun getCurrentAccessToken(): Result<*> {
        accessToken?.let {
            return Result.Success(it.token)
        }

        Timber.d("Запрошен токен, когда пользователь не был залогинен")
        return Result.Error(IOException("Нет действительного токена и обновить не удалось"))
    }

    private fun save(tokens: NetworkEntityToken) {
        accessToken = AccessTokenEntity(tokens.accessToken)

        with(sharedPrefs.edit()) {
            putString(AUTH_REFRESH_TOKEN, tokens.refreshToken)
            apply()
        }
    }

    private fun removeToken() {
        accessToken = null

        with(sharedPrefs.edit()) {
            remove(AUTH_REFRESH_TOKEN)
            apply()
        }
    }
}
