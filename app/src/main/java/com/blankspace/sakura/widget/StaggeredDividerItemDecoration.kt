package com.blankspace.sakura.widget

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StaggeredDividerItemDecoration(val context:Context):RecyclerView.ItemDecoration() {
    private val mDividerWidth = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,7f,context.resources.displayMetrics).toInt()
    private val mDividerBottom = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 30f, context.resources.displayMetrics
            ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = mDividerWidth
        outRect.right = mDividerWidth
        outRect.bottom = mDividerBottom

    }
}