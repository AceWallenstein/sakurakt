package com.blankspace.sakura.net

import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }

    @POST("user/login")
    suspend fun login(@QueryMap map: Map<String,@JvmSuppressWildcards Any>): BaseResult<String>


}