package com.blankspace.sakura.form

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.databinding.FragmentFormBinding
import com.blankspace.sakura.ext.onClick

class FormFragment : BaseFragment<FragmentFormBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFormBinding {
        return FragmentFormBinding.inflate(inflater, container, false)
    }

    override fun initView(vb: FragmentFormBinding) {
        with(vb) {
            onClick(ivDrag) {
                toast(content = "dianji")
            }
        }
    }

}