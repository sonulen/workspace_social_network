package com.redmadrobot.data.network.workspace

import com.redmadrobot.data.entity.api.NetworkEntityPosts
import com.redmadrobot.data.network.NetworkRouter
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkspaceProfileApi {
    // region FEED
    @GET(NetworkRouter.FEED_GET)
    suspend fun feedGet(): NetworkEntityPosts

    @POST(NetworkRouter.FEED_LIKE)
    suspend fun feedLike(@Path("id") id: String)

    @DELETE(NetworkRouter.FEED_DELETE_LIKE)
    suspend fun feedDeleteLike(@Path("id") id: String)

    @GET(NetworkRouter.FEED_GET_FAVORITE)
    suspend fun feedGetFavorite(): NetworkEntityPosts
    // endregion
}
