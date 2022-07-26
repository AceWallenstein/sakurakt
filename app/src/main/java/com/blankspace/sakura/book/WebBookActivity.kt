package com.blankspace.sakura.book

import android.content.Intent
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankspace.sakura.adapter.IndexAdapter
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.databinding.ActivityBookBinding
import com.blankspace.sakura.ext.onClick
import com.blankspace.sakura.form.DetailActivity
import com.blankspace.sakura.net.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WebBookActivity : BaseActivity<ActivityBookBinding>() {
    lateinit var indexAdapter: IndexAdapter

    override fun getViewBinding(): ActivityBookBinding =
        ActivityBookBinding.inflate(layoutInflater)

    override fun initView() {
        indexAdapter = IndexAdapter(listener = {
            _,lesson,position->
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra(DetailActivity.PARAM_ARTICLE,lesson)
            startActivity(intent)

        })
        with(vb) {
            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
                adapter = indexAdapter
            }
        onClick(ivBack){
            when(it){
                ivBack->{finish()}
            }
        }
        }
    }
    override fun initData() {
        val cid = intent.getIntExtra("cid", 0)

        CoroutineScope(lifecycleScope.coroutineContext).launch {

            //?cid=549&order_type=1
            val result = RetrofitClient.apiService.getLesson(
                0,
                mapOf("cid" to cid, "order_type" to 1)
            )
            indexAdapter.setData(result.apiData().datas)

        }

    }

}