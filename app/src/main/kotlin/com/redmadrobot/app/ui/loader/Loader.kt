package com.redmadrobot.app.ui.loader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnRepeat
import com.redmadrobot.app.R
import com.redmadrobot.app.utils.extension.toPx

class Loader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    // region Размеры
    private companion object {
        const val PULSE_SIZE = 2f
        const val SQUARE_SIZE = 40f
        const val SQUARE_MARGIN = 4f
        const val SQUARE_RADIUS = 14f
        const val ANIMATION_DURATION = 400L
    }

    private val pulseSize = PULSE_SIZE.toPx
    private val squareSide = SQUARE_SIZE.toPx
    private val squareMargin = SQUARE_MARGIN.toPx
    private val squareRadius = SQUARE_RADIUS.toPx
    private val viewSize = (2 * squareSide + squareMargin + 2 * pulseSize).toInt()
    // endregion

    // region Цвета
    private val lightGreyBlueColor = context.getColor(R.color.light_grey_blue)
    private val greyColor = context.getColor(R.color.grey)
    private val middleGreyColor = context.getColor(R.color.middle_grey)
    private val darkGreyColor = context.getColor(R.color.dark_grey)
    // endregion

    // Массив с квадратами
    private val squares = arrayOf(
        // Верхний левый
        Square(
            moveDirections = -pulseSize to -pulseSize,
            startRect = RectF(
                pulseSize,
                pulseSize,
                pulseSize + squareSide,
                pulseSize + squareSide
            )
        ).apply {
            paint.color = lightGreyBlueColor
        },
        // Верхний правый
        Square(
            moveDirections = pulseSize to -pulseSize,
            startRect = RectF(
                pulseSize + squareSide + squareMargin,
                pulseSize,
                pulseSize + 2 * squareSide + squareMargin,
                pulseSize + squareSide
            )
        ).apply {
            paint.color = greyColor
        },
        // Нижний левый
        Square(
            moveDirections = -pulseSize to pulseSize,
            startRect = RectF(
                pulseSize,
                pulseSize + squareSide + squareMargin,
                pulseSize + squareSide,
                pulseSize + 2 * squareSide + squareMargin
            )
        ).apply {
            paint.color = darkGreyColor
        },
        // Нижний правый
        Square(
            moveDirections = pulseSize to pulseSize,
            startRect = RectF(
                pulseSize + squareSide + squareMargin,
                pulseSize + squareSide + squareMargin,
                pulseSize + 2 * squareSide + squareMargin,
                pulseSize + 2 * squareSide + squareMargin
            )
        ).apply {
            paint.color = middleGreyColor
        }
    )

    init {
        // Анимация. Смысл его интерполируемой величины - процент от текущей пульсации
        ValueAnimator.ofFloat(0f, 1f).apply {
            interpolator = LinearInterpolator()
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = ANIMATION_DURATION
            addUpdateListener {
                val value = it.animatedValue as? Float

                value?.let { percent ->
                    onTeak(percent)
                }
            }
            start()
        }.doOnRepeat {
            colorChange()
        }
    }

    private fun colorChange() {
        for (square in squares) {
            square.paint.color = getNextColor(square.paint.color)
        }
        invalidate()
    }

    private fun onTeak(percentOfPulse: Float) {
        for (square in squares) {
            square.updateRect(percentOfPulse)
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(viewSize, viewSize)
    }

    override fun onDraw(canvas: Canvas) {
        for (square in squares) {
            canvas.drawRoundRect(square.currentRect, squareRadius, squareRadius, square.paint)
        }
    }

    private fun getNextColor(currentColor: Int): Int = when (currentColor) {
        lightGreyBlueColor -> darkGreyColor
        greyColor -> lightGreyBlueColor
        middleGreyColor -> greyColor
        darkGreyColor -> middleGreyColor
        else -> lightGreyBlueColor
    }

    private class Square(
        // Направление движения
        private val moveDirections: Pair<Float, Float>,
        // Начальное положение
        private val startRect: RectF,
    ) {
        // Положение на текущий тик
        val currentRect = RectF()
        val paint: Paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        fun updateRect(percent: Float) {
            val (x, y) = moveDirections
            currentRect.set(startRect)
            currentRect.offset(x * percent, y * percent)
        }
    }
}
