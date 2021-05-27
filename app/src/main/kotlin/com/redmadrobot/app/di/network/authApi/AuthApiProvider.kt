package com.redmadrobot.app.di.network.authApi

import com.redmadrobot.data.network.auth.AuthApi

interface AuthApiProvider {
    fun authApi(): AuthApi
}
