package com.blankspace.sakura.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blankspace.sakura.R
import com.blankspace.sakura.bean.Course
import com.blankspace.sakura.databinding.ItemPicBinding

class PicAdapter( listener: ((View, Course, Int) -> Unit)? = null): BaseAdapter<Course, PicAdapter.VH>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return VH(
           ItemPicBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        mDatas?.get(position)?.run{
            holder.binding(this)
            holder.itemView.setOnClickListener{
                listener?.invoke(it,this,position)
            }
        }

    }

    class VH(val binding: ItemPicBinding) : RecyclerView.ViewHolder(binding.root){
        fun binding(data: Course?) {
            data?.apply {
                binding.image.load(data.cover){
                    placeholder(R.drawable.ic_launcher_background)
                }
            }
        }

    }
}