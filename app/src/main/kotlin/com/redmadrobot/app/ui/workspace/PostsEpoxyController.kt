package com.redmadrobot.app.ui.workspace

import com.airbnb.epoxy.EpoxyController
import com.redmadrobot.app.ui.workspace.postsView.emptyView
import com.redmadrobot.app.ui.workspace.postsView.errorView
import com.redmadrobot.app.ui.workspace.postsView.postView
import com.redmadrobot.domain.entity.repository.Post
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class PostsEpoxyController @Inject constructor() : EpoxyController() {
    enum class STATE {
        EMPTY,
        ERROR,
        CONTENT
    }

    private var posts = mutableListOf<Post>()
    private var state = STATE.EMPTY
    private var emptyHandler: () -> Unit = {}
    private var errorHandler: () -> Unit = {}
    private var postLikeHandler: (String, Boolean) -> Unit = { _, _ -> }

    fun setEmptyView(handler: () -> Unit) {
        reset()
        state = STATE.EMPTY
        emptyHandler = handler
        requestModelBuild()
    }

    fun setErrorView(handler: () -> Unit) {
        reset()
        state = STATE.ERROR
        errorHandler = handler
        requestModelBuild()
    }

    fun setPostsList(posts: List<Post>, handler: (String, Boolean) -> Unit) {
        reset()
        state = STATE.CONTENT
        this.posts = posts.toMutableList()
        postLikeHandler = handler
        requestModelBuild()
    }

    private fun reset() {
        emptyHandler = {}
        errorHandler = {}
        postLikeHandler = { _, _ -> }
    }

    override fun buildModels() {
        when (state) {
            STATE.EMPTY -> {
                val explicitHandler = emptyHandler
                emptyView {
                    id(UUID.randomUUID().toString())
                    findFriendOnClickListener {
                        explicitHandler.invoke()
                    }
                }
            }

            STATE.ERROR -> {
                val explicitHandler = errorHandler
                errorView {
                    id(UUID.randomUUID().toString())
                    refreshOnClickListener {
                        explicitHandler.invoke()
                    }
                }
            }

            STATE.CONTENT -> {
                val explicitHandler = postLikeHandler
                val df = DecimalFormat("#.##")
                for (post in posts) {
                    postView {
                        id(post.id)
                        postText(post.text)
                        locationText("Lat: " + df.format(post.lat) + " Lon: " + df.format(post.lon))
                        author(post.author.nickname)
                        likesCountText(post.likes.toString())
                        likeState(post.liked)
                        likeOnClickListener {
                            explicitHandler.invoke(post.id, post.liked)
                        }
                    }
                }
            }
        }
    }
}
