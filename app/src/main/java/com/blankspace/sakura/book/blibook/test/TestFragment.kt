package com.blankspace.sakura.book.blibook.test;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.book.blibook.BliViewModel
import com.blankspace.sakura.book.blibook.di.MobilePhone
import com.blankspace.sakura.book.blibook.di.UserManager
import com.blankspace.sakura.databinding.FragmentTestBliBinding
import com.blankspace.sakura.ext.onClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBliBinding>() {
    private val bliViewModel by activityViewModels<BliViewModel>()
    private val testViewModel by viewModels<TestViewModel>()

    @Inject
    lateinit var userManager:UserManager
    @Inject
    lateinit var mobilePhone: MobilePhone

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTestBliBinding =
        FragmentTestBliBinding.inflate(inflater, container, false)

    override fun initView(vb: FragmentTestBliBinding) {
        with(vb){
            onClick(btDown,btCancel){
                when(view){
                    btDown->{}
                    btCancel->{}
                }
            }
        }
    }


    override fun initData() {
        userManager.getToken()
        mobilePhone.dialNumber()
    }

}