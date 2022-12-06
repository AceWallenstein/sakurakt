package com.blankspace.sakura.download

import com.blankspace.sakura.net.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DownloadRespository {

    suspend fun getData(username:String,password:String): Flow<String> {
        val map = mapOf("username" to username, "password" to password)
        val flow = flow<String> {
            val data = RetrofitClient.apiService.login(map).apiData()
            emit(data)
        }
        return flow
    }

}