package com.redmadrobot.app.di.network.workspaceApi

import com.redmadrobot.data.network.workspace.WorkspaceApi

interface WorkspaceApiProvider {
    fun workspaceApi(): WorkspaceApi
}
