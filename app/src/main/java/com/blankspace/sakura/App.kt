package com.blankspace.sakura

import android.app.Application
import com.blankspace.sakura.common.utils.CoilHelper
import com.blankspace.sakura.widget.LoadingViewDelegate
import com.dylanc.loadingstateview.LoadingStateView
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object{
        @JvmStatic
        lateinit var instance:Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CoilHelper.init(this)
        LoadingStateView.setViewDelegatePool {
            register(LoadingViewDelegate())
        }

    }


}