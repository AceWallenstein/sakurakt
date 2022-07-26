package com.blankspace.sakura.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(protected var listener: ((View, T, Int) -> Unit)? = null) : RecyclerView.Adapter<VH>() {
    protected var mDatas: MutableList<T>? = null


    public fun setData(datas: MutableList<T>) {
        mDatas = datas;
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int =
        mDatas?.size ?: 0


}
