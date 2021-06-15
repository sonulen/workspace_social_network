package com.redmadrobot.app.ui.workspace.postsView

class ClosureListener constructor(
    val postId: String,
    val postLikeState: Boolean,
    val listener: (String, Boolean) -> Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (other is ClosureListener) {
            return postId == other.postId && postLikeState == other.postLikeState
        }
        return false
    }

    override fun hashCode(): Int {
        return postId.hashCode() + postLikeState.hashCode()
    }

    fun invoke() {
        listener.invoke(postId, postLikeState)
    }
}
