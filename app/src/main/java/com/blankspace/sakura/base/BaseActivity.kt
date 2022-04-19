package com.blankspace.sakura.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected val vb: VB by lazy { getViewBinding() }

    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        initView()
        initData()
        onViewClick()
    }

    protected open fun initView() {

    }

    protected open fun initData() {

    }

    protected open fun onViewClick(){

    }

}