package com.blankspace.sakura.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.blankspace.sakura.R
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.extensions.newGenjiDialog

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    protected lateinit var vb: VB
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = getViewBinding(inflater,container)
        initView(vb)
        return vb.root
    }

    protected open fun initView(vb: VB) {

    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    protected open fun initData() {

    }

    private var loading: GenjiDialog? = null

    public open fun showProgress() {
        if (activity != null) {
            loading?.showOnWindow(requireActivity().supportFragmentManager) ?: newGenjiDialog {
                //只需要将unLeak属性设置为true
                layoutId = R.layout.dialog_mask
                unLeak = true
            }.also { loading = it }.showOnWindow(requireActivity().supportFragmentManager)
        }

    }

    public open fun closeProgress() {
        loading?.dismiss()
    }

}