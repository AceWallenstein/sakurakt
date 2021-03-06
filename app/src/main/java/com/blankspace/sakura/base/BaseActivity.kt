package com.blankspace.sakura.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ly.genjidialog.extensions.newGenjiDialog

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
    public open fun loadingDialog(){
        val testDialog =  newGenjiDialog {
            //只需要将unLeak属性设置为true
//            layoutId = R.layout.dialog_mask
            unLeak = true
        }.showOnWindow(supportFragmentManager)

    }


}