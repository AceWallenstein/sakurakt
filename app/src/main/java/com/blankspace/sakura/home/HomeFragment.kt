package com.blankspace.sakura.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

}