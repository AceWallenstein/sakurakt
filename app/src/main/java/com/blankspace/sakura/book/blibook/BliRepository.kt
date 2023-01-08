package com.blankspace.sakura.book.blibook

import com.blankspace.sakura.net.RetrofitClient

class BliRepository {
    suspend fun getHomePage():String{
        return RetrofitClient.bookApiService.getHomePage()
    }
}