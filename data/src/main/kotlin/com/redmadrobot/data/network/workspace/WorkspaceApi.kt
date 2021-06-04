package com.redmadrobot.data.network.workspace

import com.redmadrobot.data.entity.api.response.NetworkEntityUserList
import com.redmadrobot.data.network.NetworkRouter
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkspaceApi : WorkspaceMeApi, WorkspaceProfileApi {
    // region SEARCH
    @GET(NetworkRouter.SEARCH)
    suspend fun search(
        @Query("user") user: String? = null,
        @Query("post") post: String? = null,
    ): NetworkEntityUserList
    // endregion
}
