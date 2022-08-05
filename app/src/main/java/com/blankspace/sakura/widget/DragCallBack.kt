package com.blankspace.sakura.widget

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.adapter.DragAdapter
import java.util.*

class DragCallBack(val mAdapter: DragAdapter, val mData: MutableList<String>) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var dragFlag = 0
        var swipeFlag = 0
        when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                dragFlag = ItemTouchHelper.LEFT or ItemTouchHelper.UP or
                        ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlag, swipeFlag)
            }
            is LinearLayoutManager -> {
                dragFlag = ItemTouchHelper.LEFT or
                        ItemTouchHelper.RIGHT
                swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlag, swipeFlag)
            }
            else -> return 0;
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.bindingAdapterPosition
        val toPosition = target.bindingAdapterPosition
        if (fromPosition == mAdapter.fixPosition || toPosition == mAdapter.fixPosition) {
            return false
        }
        if (fromPosition < toPosition) {
            for (index in fromPosition until toPosition) {
                Collections.swap(mData, index, index + 1)
            }
        } else {
            for (index in fromPosition until toPosition) {
                Collections.swap(mData, index, index - 1)
            }
        }
        mAdapter.notifyItemChanged(fromPosition, toPosition)
        return true

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        mData.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.let {
                ViewCompat.animate(it.itemView).setDuration(200).scaleX(1.3F).scaleY(1.3F).start()
            }

        }
        super.onSelectedChanged(viewHolder, actionState)

    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                // 网格布局 设置选中大小
                ViewCompat.animate(viewHolder.itemView).setDuration(200).scaleX(1F).scaleY(1F).start()
            }
            is LinearLayoutManager -> {
                 }
        }
        super.clearView(recyclerView, viewHolder)

    }
}