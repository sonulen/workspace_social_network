package com.redmadrobot.data.network

import com.redmadrobot.data.entity.api.NetworkEntityRefreshToken
import com.redmadrobot.data.entity.api.NetworkEntityToken
import com.redmadrobot.data.entity.api.NetworkEntityUserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST(NetworkRouter.AUTH_REGISTRATION)
    suspend fun registration(@Body body: NetworkEntityUserCredentials): NetworkEntityToken

    @POST(NetworkRouter.AUTH_LOGIN)
    suspend fun login(@Body body: NetworkEntityUserCredentials): NetworkEntityToken

    /**
     * @param accessToken Токен должен передаваться строкой в формате: "Bearer $token"
     * @return Т.к. у logout нет ответа, оборачиваем Unit в Response, чтобы была возможность корректно сконвертировать
     */
    @POST(NetworkRouter.AUTH_LOGOUT)
    suspend fun logout(@Header("Authorization") accessToken: String): Response<Unit>

    @POST(NetworkRouter.AUTH_REFRESH)
    suspend fun refresh(@Body body: NetworkEntityRefreshToken): NetworkEntityToken
}
