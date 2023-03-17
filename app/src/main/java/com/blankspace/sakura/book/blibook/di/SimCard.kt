package com.blankspace.sakura.book.blibook.di

import android.util.Log
import javax.inject.Inject

class SimCard @Inject constructor() {
    private val TAG = "SimCard"
    fun dialNumber(){
        Log.d(TAG, "dialNumber")
    }
}