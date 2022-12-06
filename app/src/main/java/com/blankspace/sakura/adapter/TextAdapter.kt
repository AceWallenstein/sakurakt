package com.blankspace.sakura.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.databinding.ItemTextBinding

class TextAdapter(listener: ((View, Int, Int) -> Unit)? = null) :
    BaseAdapter<Int, TextAdapter.VH>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    public fun addData(count: Int){
        if (mDatas == null) {
            mDatas = arrayListOf()
        }
        mDatas!!.add(count)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        mDatas?.get(position)?.run {
            holder.binding(this)
        }

    }

    class VH(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Int?) {
            data?.apply {
                binding.text.text = data.toString()
            }
        }

    }
}