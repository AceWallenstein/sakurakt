package com.blankspace.sakura.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.blankspace.sakura.R
import java.lang.Math.abs

class ReadView : View {
    private var downX = 0f
    private var clickFlag = false
    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop * 5
    private var onClick: ((which: Int) -> Unit)? = null
    private var printConfig: PrintConfig? = null
    private var mContent: Content = Content(emptyList(), "", "", "")
    private val mPaint: Paint = Paint()

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout)


        typedArray.recycle()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                clickFlag = true
            }
            MotionEvent.ACTION_UP -> {
                if (clickFlag) {
                    handleActionUp(event)
                    clickFlag = false
                }
            }
            MotionEvent.ACTION_CANCEL -> clickFlag = false
            MotionEvent.ACTION_OUTSIDE -> clickFlag = false
        }

        return true
    }

    private fun handleActionUp(event: MotionEvent) {
        val offset = downX - event.x
        if (abs(offset) > mTouchSlop) {
            onClick?.let { it(LEFT_CLICK) }
        } else {
            onClick?.let { it(RIGHT_CLICK) }
        }
        when (event.x) {
            in 0f..width * 1 / 3f -> onClick?.let { it(LEFT_CLICK) }
            in width * 3 / 2f..width.toFloat() -> onClick?.let { it(LEFT_CLICK) }
            else -> onClick?.let { it(CANCEL_CLICK) }

        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null && printConfig != null) {
            val config = printConfig!!
            canvas.drawColor(config.backgroundColor)
            mContent.lines.forEachIndexed { index, line ->
                val x = config.textMarginStart
                val y =
                    config.textMarginTop + (index + 1) * (config.textSize + config.textLineSpace)
                canvas.drawText(line, x, y, mPaint)

            }
        }

    }

    companion object {
        const val LEFT_CLICK = 1
        const val RIGHT_CLICK = 2
        const val CANCEL_CLICK = 3
    }

    data class Content(
        val lines: List<String>,
        val time: String,
        val progress: String,
        val batteryLevel: String,

        )

}