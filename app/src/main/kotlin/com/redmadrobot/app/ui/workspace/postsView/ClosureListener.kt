package com.redmadrobot.app.ui.workspace.postsView

data class ClosureListener<T>(
    val data: T,
    val listener: (T) -> Unit,
)
