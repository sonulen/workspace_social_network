package com.redmadrobot.app.ui.workspace.postsView

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.redmadrobot.app.databinding.EmptyViewBinding
import com.redmadrobot.extensions.viewbinding.inflateViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding = inflateViewBinding<EmptyViewBinding>()
}
