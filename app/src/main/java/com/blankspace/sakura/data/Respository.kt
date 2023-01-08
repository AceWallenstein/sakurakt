package com.blankspace.sakura.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

object Respository {
    fun simulateData(): Flow<PagingData<Int>> {
        return Pager(config = PagingConfig(10),
           pagingSourceFactory =  { SimulatePageDataSource(SimulateServer()) }).flow
    }
}