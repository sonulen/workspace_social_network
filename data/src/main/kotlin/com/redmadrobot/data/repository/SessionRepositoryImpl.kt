package com.redmadrobot.data.repository

import android.content.SharedPreferences
import com.redmadrobot.domain.entity.repository.Tokens
import com.redmadrobot.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences,
) : SessionRepository {
    companion object {
        private const val AUTH_REFRESH_TOKEN = "AUTH_REFRESH_TOKEN"
    }

    private var accessToken: String? = null

    /**
     * /see [SessionRepository.saveSession]
     */
    override fun saveSession(tokens: Tokens) {
        this.accessToken = tokens.access

        with(sharedPrefs.edit()) {
            putString(AUTH_REFRESH_TOKEN, tokens.refresh)
            apply()
        }
    }

    /**
     * /see [SessionRepository.getAccessToken]
     */
    override fun getAccessToken(): String? = accessToken

    /**
     * /see [SessionRepository.getRefreshToken]
     */
    override fun getRefreshToken(): String? = sharedPrefs.getString("key_name", null)

    /**
     * /see [SessionRepository.sessionExists]
     */
    override fun sessionExists(): Boolean = getRefreshToken() != null

    /**
     * /see [SessionRepository.clear]
     */
    override fun clear() {
        accessToken = null
        with(sharedPrefs.edit()) {
            remove(AUTH_REFRESH_TOKEN)
            apply()
        }
    }
}
