package com.redmadrobot.app.di.network

import com.redmadrobot.data.network.auth.AuthApi
import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.squareup.moshi.Moshi

interface NetworkProvider {
    fun moshi(): Moshi
    fun authApi(): AuthApi
    fun workspaceApi(): WorkspaceApi
}
