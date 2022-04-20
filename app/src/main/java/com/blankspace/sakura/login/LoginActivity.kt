package com.blankspace.sakura.login

import androidx.activity.viewModels
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.databinding.ActivityLoginBinding
import com.blankspace.sakura.ext.onClick

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    val vm: LoginViewModel by viewModels()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun onViewClick() {
        vb.apply {
            onClick(button) {
                when (it.id) {
                    R.id.button -> {
                        vm.login("1", "123")


                    }
                }
            }
        }
    }


}