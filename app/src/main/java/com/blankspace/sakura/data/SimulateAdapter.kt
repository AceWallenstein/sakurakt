package com.blankspace.sakura.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.R

class SimulateAdapter() : PagingDataAdapter<Int, SimulateAdapter.VH>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return true
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_drag,parent,false)
        )



    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder){
            text.text = "${getItem(position)}"
        }
    }


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.text)
    }

}