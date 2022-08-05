package com.blankspace.sakura.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.R

class DragAdapter(private val mContext:Context,private val mList: List<String>) : RecyclerView.Adapter<DragAdapter.VH>() {
    val fixPosition = 0 //固定位置

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_drag,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.text.text = mList[position]
        val drawable = holder.text.background as ColorDrawable
        if (holder.adapterPosition == 0) {
            drawable.color = ContextCompat.getColor(mContext, R.color.bgColorPrimary)
        }else{
            drawable.color = ContextCompat.getColor(mContext, R.color.bgColorThird)
        }

    }

    override fun getItemCount(): Int = mList.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val text: TextView by lazy(LazyThreadSafetyMode.NONE) { itemView.findViewById(R.id.text) }

    }


}