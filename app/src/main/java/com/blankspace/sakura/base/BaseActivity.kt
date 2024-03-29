package com.blankspace.sakura.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.extensions.newGenjiDialog

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var mContext: Context
    protected lateinit var vb: VB

    abstract fun getViewBinding(): VB

    private var loading: GenjiDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentViewWithBinding()
        initView()
        initData()
        onViewClick()
    }

    protected open fun setContentViewWithBinding()
    {
        vb = getViewBinding()
        setContentView(vb.root)
    }

    protected open fun initToolBar() {

    }

    protected open fun initView() {

    }

    protected open fun initData() {

    }

    protected open fun onViewClick() {

    }

    public open fun showProgress() {
        loading?.showOnWindow(supportFragmentManager) ?: newGenjiDialog {
            //只需要将unLeak属性设置为true
//            layoutId = R.layout.dialog_mask
            unLeak = true
        }.also { loading = it }.showOnWindow(supportFragmentManager)

    }

    public open fun closeProgress() {
        loading?.dismiss()
    }


}