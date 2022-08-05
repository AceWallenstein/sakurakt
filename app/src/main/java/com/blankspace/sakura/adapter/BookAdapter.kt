package com.blankspace.sakura.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.bean.Book
import com.blankspace.sakura.databinding.ItemBookBinding

class BookAdapter(private var listener: ((View, Book, Int) -> Unit)? = null) : PagingDataAdapter<Book, BookAdapter.VH>(BOOK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        listener?.run {
            holder.itemView.setOnClickListener{
                this.invoke(it,getItem(position)!!,position)
            }
        }
    }

    class VH(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Book?) {
            data?.apply {
               binding.tvTitle.text = data.name
            }
        }

    }

}
val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        // User ID serves as unique ID
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
        // Compare full contents (note: Java users should call .equals())
        oldItem == newItem
}