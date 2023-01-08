package com.blankspace.sakura.book.blibook

import android.text.method.ScrollingMovementMethod
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.databinding.ActivityBlibookBinding

class BliBookActivity : BaseActivity<ActivityBlibookBinding>() {


    private val bliViewModel by lazy(LazyThreadSafetyMode.NONE) {
        BliViewModel()
    }
    override fun getViewBinding(): ActivityBlibookBinding =
        ActivityBlibookBinding.inflate(layoutInflater)

    override fun initView() {
        initNavigation()
        with(vb){
            tvHtml.movementMethod = ScrollingMovementMethod.getInstance()

            bliViewModel.content.observe(this@BliBookActivity){
                tvHtml.text = it
            }
        }
    }

    override fun initData() {
        bliViewModel.launchHomePage()
    }

    private fun initNavigation(){
        with(vb){
            navigation.menu.clear()
            navigation.menu.add(0,1,0,"home")
            navigation.menu.add(0,2,0,"book")
            navigation.menu.add(0,3,0,"ranking list")
            navigation.menu.add(0,4,0,"mine")
        }
    }
}