package com.blankspace.sakura.download

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankspace.sakura.adapter.DownloadAdapter
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.databinding.ActivityDownloadBinding

class DownloadActivity : BaseActivity<ActivityDownloadBinding>() {
    private var mAdapter:DownloadAdapter? = null
    private val vm by  lazy(LazyThreadSafetyMode.NONE){
        ViewModelProvider(this).get(DownloadViewModel::class.java)
    }

    override fun getViewBinding(): ActivityDownloadBinding = ActivityDownloadBinding.inflate(layoutInflater)
    override fun initView() {
        with(vb){
            mAdapter = DownloadAdapter()
            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                adapter =mAdapter
            }
        }
        vb.lifecycleOwner = this
        vb.viewmodel = vm
        vm.loading.observe(this){
            if(it){
                showProgress()
            }else{
                closeProgress()
            }
        }
    }

    override fun initData() {
            vm.getData("","")
    }
}