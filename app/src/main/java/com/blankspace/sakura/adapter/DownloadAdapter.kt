package com.blankspace.sakura.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.databinding.ItemDownloadBinding

class DownloadAdapter() : BaseAdapter<String, DownloadAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ItemDownloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }
    class VH(val binding: ItemDownloadBinding) : RecyclerView.ViewHolder(binding.root){


    }

}