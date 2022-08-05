package com.blankspace.sakura.book

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.data.BookRepository

class BookListViewModel(private val bookRepos: BookRepository):BaseViewModel() {
    val bookFlow = Pager(PagingConfig(pageSize = 10)){
        bookRepos.bookInPaging()
    }.flow
}