@file:Suppress(
    "MagicNumber",
    "UnusedPrivateMember"
)

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
import androidx.core.animation.doOnRepeat
import com.redmadrobot.app.R

class Loader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    // region Размеры

    // Шаг пульсации
    private val pulseStep = pxToDp(1f)

    // Сколько пульсаций в одном направлении
    private val pulseStepCount = 2

    // Размер всей пульсации
    private val pulseSize = pulseStep * pulseStepCount

    // Где находится старт верхнего левого квадрата учитывая его пульсацию к 0;0
    private val start = pulseSize

    // Длина одного квадрата
    private val side: Float = pxToDp(40f)

    // Отступ между квадратами
    private val margin: Float = pxToDp(4f)

    // Скругление
    private val round: Float = pxToDp(10f)

    // Величина всей вьюхи
    private val viewSize = (2 * side + margin + 2 * pulseSize).toInt()
    // endregion

    // region Цвета
    private val lightGreyBlueColor: Int = context.getColor(R.color.light_grey_blue)
    private val greyColor = context.getColor(R.color.grey)
    private val middleGreyColor = context.getColor(R.color.middle_grey)
    private val darkGreyColor = context.getColor(R.color.dark_grey)
    // endregion

    // Массив с квадратами
    private val squares = arrayOf(
        // Верхний левый
        Square(
            moveDirections = -pulseSize to -pulseSize,
        ).apply {
            startRect.set(start, start, start + side, start + side)
            paint.color = lightGreyBlueColor
        },
        // Верхний правый
        Square(
            moveDirections = pulseSize to -pulseSize
        ).apply {
            startRect.set(start + side + margin, start + 0f, start + 2 * side + margin, start + side)
            paint.color = greyColor
        },
        // Нижний левый
        Square(
            moveDirections = -pulseSize to pulseSize,
        ).apply {
            startRect.set(start + 0f, start + side + margin, start + side, start + 2 * side + margin)
            paint.color = darkGreyColor
        },
        // Нижний правый
        Square(
            moveDirections = pulseSize to pulseSize,
        ).apply {
            startRect.set(
                start + side + margin,
                start + side + margin,
                start + 2 * side + margin,
                start + 2 * side + margin
            )
            paint.color = middleGreyColor
        }
    )

    // Анимация. Смысл его интерполируемой величины - процент от текущей пульсации
    private val valueAnimation = ValueAnimator.ofFloat(0f, 100f).apply {
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
        repeatMode = ValueAnimator.REVERSE
        duration = 400
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

    private fun colorChange() {
        for (square in squares) {
            square.paint.color = getNextColor(square.paint.color)
        }
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
            canvas.drawRoundRect(square.currentRect, round, round, square.paint)
        }
    }

    private fun getNextColor(currentColor: Int): Int = when (currentColor) {
        lightGreyBlueColor -> darkGreyColor
        greyColor -> lightGreyBlueColor
        middleGreyColor -> greyColor
        darkGreyColor -> middleGreyColor
        else -> lightGreyBlueColor
    }

    private fun pxToDp(px: Float) = px * Resources.getSystem().displayMetrics.density

    private class Square(
        private val moveDirections: Pair<Float, Float>,
        // Начальное положение
        val startRect: RectF = RectF(),
    ) {
        // Положение на текущий тик
        var currentRect = RectF()
        val paint: Paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        fun updateRect(percent: Float) {
            val (x, y) = moveDirections
            currentRect.set(startRect)
            currentRect.offset(x * 0.01f * percent, y * 0.01f * percent)
        }
    }
}
