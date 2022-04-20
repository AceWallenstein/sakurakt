package com.blankspace.sakura

import android.app.Application


class App : Application() {
    companion object{
        @JvmStatic
        lateinit var instance:Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


}