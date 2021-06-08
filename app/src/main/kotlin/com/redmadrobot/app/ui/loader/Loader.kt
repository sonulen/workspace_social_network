package com.redmadrobot.app.ui.loader

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.redmadrobot.app.R

class Loader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    // region Размеры
    private val pulseSize = pxToDp(2f)
    private val start = pulseSize
    private val side: Float = pxToDp(40f)
    private val margin: Float = pxToDp(8f)
    private val round: Float = pxToDp(10f)
    private val viewSize = (2 * side + margin + 2 * pulseSize).toInt()
    // endregion

    // region Цвета
    private val lightGreyBlueColor = context.getColor(R.color.light_grey_blue)
    private val greyColor = context.getColor(R.color.grey)
    private val middleGreyColor = context.getColor(R.color.middle_grey)
    private val darkGreyColor = context.getColor(R.color.dark_grey)
    // endregion

    private val squares = arrayOf(
        Square().apply {
            rect.set(start, start, start + side, start + side)
            paint.color = lightGreyBlueColor
        },
        Square().apply {
            rect.set(start + side + margin, start + 0f, start + 2 * side + margin, start + side)
            paint.color = greyColor
        },
        Square().apply {
            rect.set(start + 0f, start + side + margin, start + side, start + 2 * side + margin)
            paint.color = darkGreyColor
        },
        Square().apply {
            rect.set(start + side + margin, start + side + margin, start + 2 * side + margin, start + 2 * side + margin)
            paint.color = middleGreyColor
        }
    )

    private val valueAnimation = ValueAnimator.ofFloat(-pulseSize, pulseSize).apply {
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
        repeatMode = ValueAnimator.REVERSE
        addUpdateListener { it -> onTeak(it) }
        start()
    }

    private fun onTeak(valueAnimator: ValueAnimator) {
        val value = valueAnimator.animatedValue as? Float

        value?.let {
            // TODO тут offsetTo должен быть
            squares[0].rect.offset(-it, -it)
            squares[3].rect.offset(it, it)
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(viewSize, viewSize)
    }

    override fun onDraw(canvas: Canvas) {
        for (square in squares) {
            canvas.drawRoundRect(square.rect, round, round, square.paint)
        }
    }

    private fun pxToDp(px: Float) = px * Resources.getSystem().displayMetrics.density

    private class Square {
        val rect = RectF()
        val paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }
}
