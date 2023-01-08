package com.blankspace.sakura.book

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.data.BookRepository
import com.blankspace.sakura.data.Respository
import com.blankspace.sakura.data.SimulateServer
import kotlinx.coroutines.flow.Flow

class BookListViewModel(private val bookRepos: BookRepository):BaseViewModel() {
    val bookFlow = Pager(PagingConfig(pageSize = 10)){
        bookRepos.bookInPaging()
    }.flow

    private val simulateRepository:SimulateServer = SimulateServer()

    fun simulate(pos: Int): List<Int>? {
        return simulateRepository.simulateData(pos)
    }
    fun pagerSimulate(pos: Int): Flow<PagingData<Int>> {
        return Respository.simulateData().cachedIn(viewModelScope)
    }
}