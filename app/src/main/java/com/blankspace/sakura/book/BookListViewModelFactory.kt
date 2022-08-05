package com.blankspace.sakura.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankspace.sakura.data.BookRepository

class BookListViewModelFactory(private val bookRepository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookListViewModel(bookRepository) as T
    }
}