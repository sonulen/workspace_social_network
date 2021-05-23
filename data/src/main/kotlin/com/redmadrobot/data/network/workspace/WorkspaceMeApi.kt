package com.redmadrobot.data.network.workspace

import com.redmadrobot.data.entity.api.NetworkEntityPosts
import com.redmadrobot.data.entity.api.request.NetworkEntityAddFriend
import com.redmadrobot.data.entity.api.request.NetworkEntityUserProfile
import com.redmadrobot.data.entity.api.response.NetworkEntityPostItem
import com.redmadrobot.data.entity.api.response.NetworkEntityUserList
import com.redmadrobot.data.network.NetworkRouter
import retrofit2.http.*

interface WorkspaceMeApi {
    // region ME
    @GET(NetworkRouter.ME_GET_PROFILE)
    suspend fun meGetProfile(): NetworkEntityUserProfile

    @PATCH(NetworkRouter.ME_CHANGE_PROFILE)
    @Multipart
    suspend fun mePatchProfile(
        @Part("nickname") nickname: String,
        @Part("first_name") firstName: String,
        @Part("last_name") lastName: String,
        @Part("birth_day") birthday: String,
        @Part("avatar_file") avatarFile: String = "",
    ): NetworkEntityUserProfile

    @GET(NetworkRouter.ME_GET_FRIEND_LIST)
    suspend fun meGetFriendList(): NetworkEntityUserList

    @POST(NetworkRouter.ME_ADD_FRIEND)
    suspend fun meAddFriend(@Body networkEntityAddFriend: NetworkEntityAddFriend)

    @DELETE(NetworkRouter.ME_DELETE_FRIEND)
    suspend fun meDeleteFriend(@Path("id") id: String)

    @GET(NetworkRouter.ME_GET_POSTS)
    suspend fun meGetPosts(): NetworkEntityPosts

    @POST(NetworkRouter.ME_ADD_POST)
    @Multipart
    suspend fun meAddPost(
        @Part("text") text: String,
        @Part("lat") lat: Double,
        @Part("lon") lon: Double,
        @Part("image_file") imageFile: String = "",
    ): NetworkEntityPostItem
    // endregion
}
