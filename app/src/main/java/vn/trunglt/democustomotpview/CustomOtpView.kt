package vn.trunglt.democustomotpview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
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
        }
    }
    private val letterWrapperRectF by lazy {
        RectF()
    }
    private var horizontalDelta = 0f
    private var letterWrapperWidth = 0f
    private var currentX = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBackgroundDrawable(null)
    }

    override fun onDraw(canvas: Canvas) {
        horizontalDelta = (width / OTP_LENGTH).toFloat()
        drawLetterWrappers(canvas)
    }

    private fun drawLetterWrappers(canvas: Canvas) {
        letterWrapperWidth = ((measuredWidth - (OTP_LENGTH - 1) * LETTER_WRAPPER_PADDING) / OTP_LENGTH).toFloat()
        currentX = 0f
        for (i in 0 until OTP_LENGTH) {
            letterWrapperRectF.set(
                currentX,
                0f,
                currentX + letterWrapperWidth,
                measuredHeight.toFloat()
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
}