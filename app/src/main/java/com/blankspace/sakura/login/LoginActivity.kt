package com.blankspace.sakura.login

import android.content.Intent
import androidx.activity.viewModels
import com.blankspace.sakura.MainActivity
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.databinding.ActivityLoginBinding
import com.blankspace.sakura.ext.onClick

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    val vm: LoginViewModel by viewModels()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {
        vm.login_state.observe(this ){
            if(it){
                vb.tvInfo.text="登录成功"
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                toast("账号或密码错误")
            }
        }
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