package com.blankspace.sakura.book.blibook.di

import android.util.Log
import javax.inject.Inject

class UserManager @Inject constructor() {
    val TAG = "UserManager"
    fun getToken(){
        Log.d(TAG, "获取用户Token")
    }
}