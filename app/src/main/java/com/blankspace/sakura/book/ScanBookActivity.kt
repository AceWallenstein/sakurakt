package com.blankspace.sakura.book

import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.databinding.ActivityScanBookBinding
import com.blankspace.sakura.scan.ScannerFragment

class ScanBookActivity : BaseActivity<ActivityScanBookBinding>() {

    override fun getViewBinding(): ActivityScanBookBinding
    = ActivityScanBookBinding.inflate(layoutInflater)

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace( R.id.fl_container,ScannerFragment())
            .commitAllowingStateLoss()
    }
}