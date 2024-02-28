package vn.trunglt.democustomotpview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText


class CustomOtpView(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {
        companion object {
            const val OTP_LENGTH = 6
            const val LETTER_WRAPPER_WIDTH = 160f
            const val LETTER_WRAPPER_PADDING = 40
        }
    private val textPaint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            this@apply.textSize = this@CustomOtpView.textSize
        }
    }
    private val letterWrapperPaint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.parseColor("#3c3c3c")
            style = Paint.Style.STROKE
            strokeWidth = 4f
//            pathEffect = CornerPathEffect(50f)
        }
    }
    private val letterWrapperRectF by lazy {
        RectF()
    }
    private var horizontalDelta = 0f
    private var letterWrapperWidth = 0f
    private var currentX = 0f

    init {
        setBackgroundDrawable(null)
        isLongClickable = false
        isCursorVisible = false
        movementMethod = null
        setTextIsSelectable(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        setMeasuredDimension(measureDimension(desiredWidth, widthMeasureSpec), measureDimension(desiredHeight, heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        horizontalDelta = (width / OTP_LENGTH).toFloat()
//        canvas.drawArc(0f, 0f, 400f, 400f, 180f, 90f, false, letterWrapperPaint);
        drawLetterWrappers(canvas)
    }

    fun clear() {
        text?.clear()
        invalidate()
    }

    private fun drawLetterWrappers(canvas: Canvas) {
        letterWrapperWidth = ((measuredWidth - (OTP_LENGTH - 1) * LETTER_WRAPPER_PADDING) / OTP_LENGTH).toFloat()
        currentX = 0f
        for (i in 0 until OTP_LENGTH) {
            letterWrapperRectF.set(
                currentX + paddingStart,
                paddingTop.toFloat(),
                currentX + letterWrapperWidth - paddingEnd,
                measuredHeight.toFloat() - paddingBottom
            )
            canvas.drawRoundRect(letterWrapperRectF, 16f, 16f, letterWrapperPaint)
            if (i < text.toString().length) {
                canvas.drawText(
                    text.toString()[i].toString(),
                    letterWrapperRectF.centerX() - textSize / 4,
                    letterWrapperRectF.centerY() + textSize / 4,
                    textPaint
                )
            } else if (i == text.toString().length) {
                canvas.drawText(
                    "|",
                    letterWrapperRectF.centerX() - textSize / 4,
                    letterWrapperRectF.centerY() + textSize / 4,
                    textPaint
                )
            }
            currentX += (LETTER_WRAPPER_PADDING + letterWrapperWidth)
        }
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }
}