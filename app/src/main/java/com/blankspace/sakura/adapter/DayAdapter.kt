package com.blankspace.sakura.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.databinding.ItemDaysBinding

class DayAdapter : RecyclerView.Adapter<DayAdapter.VH>() {
    private var mDatas: MutableList<Int>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemDaysBinding.inflate(LayoutInflater.from(parent.context)))

    public fun setData(datas: MutableList<Int>)
    {
        mDatas = datas;
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(mDatas?.get(position)!!)
    }

    override fun getItemCount(): Int =
        mDatas?.size ?: 0


    class VH(val binding: ItemDaysBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(day: Int) {
            binding.text.text = day.toString()
        }

    }
}
