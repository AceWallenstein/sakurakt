package com.blankspace.sakura.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var mContext: Context
    protected val vb: VB by lazy { getViewBinding() }

    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(vb.root)
        initView()
        initData()
        onViewClick()
    }

    protected open fun initView() {

    }

    protected open fun initData() {

    }

    protected open fun onViewClick() {

    }

}