package com.blankspace.sakura.book.blibook

import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.databinding.ActivityBlibookBinding

class BliBookActivity : BaseActivity<ActivityBlibookBinding>() {
    override fun getViewBinding(): ActivityBlibookBinding =
        ActivityBlibookBinding.inflate(layoutInflater)

    override fun initView() {
        with(vb){

        }
    }
}