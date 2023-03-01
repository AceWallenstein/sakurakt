package com.blankspace.sakura.book.blibook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.adapter.BaseAdapter
import com.blankspace.sakura.bean.Lesson
import com.blankspace.sakura.databinding.ItemWebBookBinding

class BliIndexAdapter(listener: ((View, Lesson, Int) -> Unit)? = null) : BaseAdapter<Lesson, BliIndexAdapter.VH>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemWebBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder.binding) {
            mDatas?.get(position)?.run {
                text.text = this.title
                holder.itemView.setOnClickListener {
                    listener?.invoke(it,this,position)
                }
            }
        }
    }
    class VH(val binding: ItemWebBookBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(){

        }
    }
}