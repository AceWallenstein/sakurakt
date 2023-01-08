package com.blankspace.sakura.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class SimulatePageDataSource(val simulateRepository: SimulateServer) :
    PagingSource<Int, Int>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Int> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        val simulateData = simulateRepository.simulateData(page, pageSize)
        return if (simulateData != null) {
            val prevKey = if (page - 1 == 0) null else page - 1
            val nextKey = if (page + 1 == simulateData.size - 1) null else page + 1
            LoadResult.Page(simulateData, prevKey, nextKey)
        } else {
            LoadResult.Error(Exception())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Int>): Int? = null
}