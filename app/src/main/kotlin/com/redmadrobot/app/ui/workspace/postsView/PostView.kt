package com.redmadrobot.app.ui.workspace.postsView

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.PostViewBinding
import com.redmadrobot.extensions.viewbinding.inflateViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PostView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding = inflateViewBinding<PostViewBinding>()

    @TextProp
    fun setPostText(text: CharSequence?) {
        binding.postText.text = text
    }

    @TextProp
    fun setLocationText(text: CharSequence?) {
        binding.postLocationText.text = text
    }

    @TextProp
    fun setAuthor(text: CharSequence?) {
        binding.authorText.text = text
    }

    @TextProp
    fun setLikesCountText(text: CharSequence?) {
        binding.likesCountText.text = text
    }

    @ModelProp
    fun setLikeState(isLiked: Boolean) {
        binding.likeButton.setImageResource(
            if (isLiked) R.drawable.icon_like_enable else R.drawable.icon_like_disable
        )
    }

    @ModelProp
    fun setLikeOnClickListener(listener: ClosureListener<Pair<String, Boolean>>?) {
        binding.likeButton.setOnClickListener { _ ->
            listener?.listener?.invoke(listener.data)
        }
    }
}
