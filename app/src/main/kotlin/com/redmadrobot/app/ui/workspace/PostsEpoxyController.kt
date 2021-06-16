package com.redmadrobot.app.ui.workspace

import com.airbnb.epoxy.EpoxyController
import com.redmadrobot.app.ui.workspace.postsView.ClosureListener
import com.redmadrobot.app.ui.workspace.postsView.emptyView
import com.redmadrobot.app.ui.workspace.postsView.errorView
import com.redmadrobot.app.ui.workspace.postsView.postView
import com.redmadrobot.domain.entity.repository.Post
import javax.inject.Inject

class PostsEpoxyController @Inject constructor() : EpoxyController() {
    enum class STATE {
        EMPTY,
        ERROR,
        CONTENT
    }

    private var posts = listOf<Post>()
    private var state = STATE.EMPTY
    private var emptyHandler: () -> Unit = {}
    private var errorHandler: () -> Unit = {}
    private var postLikeHandler: (String, Boolean) -> Unit = { _, _ -> }

    fun setListeners(
        emptyHandler: () -> Unit,
        errorHandler: () -> Unit,
        postLikeHandler: (String, Boolean) -> Unit,
    ) {
        this.emptyHandler = emptyHandler
        this.errorHandler = errorHandler
        this.postLikeHandler = postLikeHandler
    }

    fun resetHandlers() {
        emptyHandler = {}
        errorHandler = {}
        postLikeHandler = { _, _ -> }
    }

    fun setEmptyView() {
        state = STATE.EMPTY
        requestModelBuild()
    }

    fun setErrorView() {
        state = STATE.ERROR
        requestModelBuild()
    }

    fun setPostsList(posts: List<Post>) {
        state = STATE.CONTENT
        this.posts = posts.toMutableList()
        requestModelBuild()
    }

    override fun buildModels() {
        when (state) {
            STATE.EMPTY -> {
                val explicitHandler = emptyHandler
                emptyView {
                    id("empty_view")
                    findFriendOnClickListener {
                        explicitHandler.invoke()
                    }
                }
            }

            STATE.ERROR -> {
                val explicitHandler = errorHandler
                errorView {
                    id("error_view")
                    refreshOnClickListener {
                        explicitHandler.invoke()
                    }
                }
            }

            STATE.CONTENT -> {
                val explicitHandler = postLikeHandler
                for (post in posts) {
                    postView {
                        id(post.id)
                        postText(post.text)
                        author(post.author.nickname)
                        likesCountText(post.likes.toString())
                        locationText(post.location)
                        likeState(post.liked)
                        likeOnClickListener(
                            ClosureListener(post.id, post.liked, explicitHandler)
                        )
                    }
                }
            }
        }
    }
}
