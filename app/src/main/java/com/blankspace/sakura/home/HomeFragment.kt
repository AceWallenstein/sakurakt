package com.blankspace.sakura.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankspace.sakura.MainActivity
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.databinding.FragmentHomeBinding
import com.blankspace.sakura.ext.onClick

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun initView(vb: FragmentHomeBinding) {
        with(vb){
        onClick(bt1,bt2,bt3)
        {
            when(it){
                bt1 ->{(activity as? MainActivity)?.loadingDialog()}
                bt2 ->{(activity as? MainActivity)?.loadingDialog()}
                bt3 ->{(activity as? MainActivity)?.loadingDialog()}
            }
        }

        }
    }


}