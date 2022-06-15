package com.blankspace.sakura

import android.app.Application
import com.blankspace.sakura.common.utils.CoilHelper


class App : Application() {
    companion object{
        @JvmStatic
        lateinit var instance:Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CoilHelper.init(this)

    }


}