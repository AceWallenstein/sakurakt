package com.blankspace.sakura.book.blibook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.adapter.BaseAdapter
import com.blankspace.sakura.book.blibook.model.Book
import com.blankspace.sakura.common.utils.GlideUtils
import com.blankspace.sakura.databinding.ItemBliBookBinding

class BookAdapter(listener: ((View, Book, Int) -> Unit)? = null) :
    BaseAdapter<Book, BookAdapter.VH>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBliBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(mDatas?.get(position))
        listener?.run {
            holder.itemView.setOnClickListener {
                this.invoke(it, mDatas?.get(position)!!, position)
            }
        }
    }

    class VH(val binding: ItemBliBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) {
            data?.apply {
                binding.tvTitle.text = data.title
                binding.tvDes.text = data.des
                binding.tvAuthor.text = data.author
//                Glide.with(binding.ivCover.context).load(data.cover).into(binding.ivCover)
                GlideUtils.loadRoundCircleImage(binding.ivCover.context, data.cover, binding.ivCover,12f)
            }
        }

    }

}

val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        // User ID serves as unique ID
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
        // Compare full contents (note: Java users should call .equals())
        oldItem == newItem
}