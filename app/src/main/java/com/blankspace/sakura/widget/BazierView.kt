package com.blankspace.sakura.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class BazierView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val path = Path()
    private var width = 0f
    private var height = 0f
    private var quadY = 0f
    private var cubicY = 0f

    private var per = 1.0f
    private var quadHeight = 100f
    private var cubicHeight = 200f


    init {
        paint.run {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            isDither = true
            isAntiAlias = true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        width = w.toFloat()
        height = h.toFloat()

        quadY = height / 4
        cubicY = height - height / 4
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var quadStart = 0f
        path.reset()
        path.moveTo(quadStart, quadY)
        while (quadStart <= width) {
            path.quadTo(quadStart + 75f, quadY - quadHeight * per, quadStart + 150f, quadY)
            path.quadTo(quadStart + 225f, quadY + quadHeight * per, quadStart + 300f, quadY)
            quadStart += 300
        }
        paint.color = Color.BLUE
        canvas?.drawPath(path, paint)
        path.reset()
        var cubicStart = 0f
        path.moveTo(cubicStart, cubicY)
        while (cubicStart <= width) {
            path.cubicTo(
                cubicStart + 100f,
                cubicY - cubicHeight * per,
                cubicStart + 200f,
                cubicY + cubicHeight * per,
                cubicStart + 300f,
                cubicY
            )
            cubicStart += 300f
        }
        paint.color = Color.RED
        canvas?.drawPath(path, paint);

    }
}