package com.redmadrobot.data.network.auth

import com.redmadrobot.data.entity.api.request.NetworkEntityRefreshToken
import com.redmadrobot.data.entity.api.request.NetworkEntityUserCredentials
import com.redmadrobot.data.entity.api.request.NetworkEntityUserProfile
import com.redmadrobot.data.entity.api.response.NetworkEntityToken
import com.redmadrobot.data.network.NetworkRouter
import retrofit2.Response
import retrofit2.http.*

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

    /**
     * @param accessToken Токен должен передаваться строкой в формате: "Bearer $token"
     * @return NetworkEntityUserProfile данные профиля
     */
    @PATCH(NetworkRouter.ME_CHANGE_PROFILE)
    @Multipart
    suspend fun mePatchProfile(
        @Header("Authorization") accessToken: String,
        @Part("nickname") nickname: String,
        @Part("first_name") firstName: String,
        @Part("last_name") lastName: String,
        @Part("birth_day") birthday: String,
        @Part("avatar_file") avatarFile: String = "",
    ): NetworkEntityUserProfile
}
