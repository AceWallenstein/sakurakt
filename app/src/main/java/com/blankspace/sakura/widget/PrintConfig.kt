package com.blankspace.sakura.widget

import android.graphics.Color
import kotlin.math.min

data class PrintConfig(
    val textSize: Float,
    val textMarginStart: Float,
    val textMarginEnd: Float,
    val textMarginTop: Float,
    val textMarginBottom: Float,
    val textLineSpace: Float,
    val bottomBarHeight: Float,
    val textColor: Int,
    val backgroundColor: Int


) {

    fun increaseTextSize(density: Float): PrintConfig {
        val increase = density * 2
        val newValue = min(textSize + increase, 40 * density)
        val lineSpace = newValue / 3
        return this.copy(textSize = newValue, textLineSpace = lineSpace)

    }

    fun decreaseTextSize(density: Float): PrintConfig {
        val decrease = density * 2
        val newValue = min(textSize - decrease, 8 * density)
        val lineSpace = newValue / 3
        return this.copy(textSize = newValue, textLineSpace = lineSpace)

    }
    companion object{
        fun default(density: Float): PrintConfig{
            val textSize = 14 * density
            val marginStart = 12 * density
            val marginEnd = 12 * density
            val marginTop = 12 * density
            val marginBottom = 12 * density
            val textLineSpace = textSize/3
            val bottomBarHeight = 24 * density
            val textColor = Color.BLACK
            val backgroundColor = Color.WHITE
            return PrintConfig(
                textSize = textSize,
                textMarginStart = marginStart,
                textMarginEnd = marginEnd,
                textMarginTop = marginTop,
                textMarginBottom = marginBottom,
                textLineSpace, bottomBarHeight, textColor, backgroundColor
            )
        }
    }
}