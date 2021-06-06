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

    private var posts: List<Post> = emptyList()
    private var state = STATE.EMPTY

    init {
        requestModelBuild()
    }

    fun setEmptyView() {
        state = STATE.EMPTY
        posts = emptyList()
        requestModelBuild()
    }

    fun setErrorView() {
        state = STATE.ERROR
        posts = emptyList()
        requestModelBuild()
    }

    fun setPostsList(posts: List<Post>) {
        state = STATE.CONTENT
        this.posts = posts
        requestModelBuild()
    }

    override fun buildModels() {
        when (state) {
            STATE.EMPTY -> emptyView {
                id(UUID.randomUUID().toString())
            }
            STATE.ERROR -> errorView {
                id(UUID.randomUUID().toString())
            }

            STATE.CONTENT -> {
                val df = DecimalFormat("#.##")
                for (post in posts) {
                    postView {
                        id(post.id)
                        postText(post.text)
                        locationText("Lat: " + df.format(post.lat) + " Lon: " + df.format(post.lon))
                        author(post.author.nickname)
                        likesCountText(post.likes.toString())
                        likeState(post.liked)
                    }
                }
            }
        }
    }
}
