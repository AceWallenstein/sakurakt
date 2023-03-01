package com.blankspace.sakura.book.blibook.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.book.blibook.BliViewModel
import com.blankspace.sakura.book.blibook.adapter.BookAdapter
import com.blankspace.sakura.book.blibook.book.WebBookActivity
import com.blankspace.sakura.book.blibook.utils.Parser
import com.blankspace.sakura.databinding.FragmentQuanziBinding

class QuanZiFragment : BaseFragment<FragmentQuanziBinding>() {
    private val bliViewModel by activityViewModels<BliViewModel>()
    private val viewModel by viewModels<QuanZiViewModel>()

    val adapter by lazy(LazyThreadSafetyMode.NONE) {
        BookAdapter { v, book, pos ->
            val intent = Intent(context, WebBookActivity::class.java)
            intent.putExtra("book", book);
            startActivity(intent)
//            toast(content = "点击了${book.url + "/catalog"}")
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuanziBinding =
        FragmentQuanziBinding.inflate(inflater, container, false)

    override fun initView(vb: FragmentQuanziBinding) {
        with(vb) {
            refresh.setColorSchemeResources(R.color.colorPrimary)
            refresh.setOnRefreshListener {
                viewModel.clear()
                viewModel.getBook(Parser.icons[0].url!!)
            }

            //recycler
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun initData() {

        bliViewModel.urlComplete.observe(this) {
            if (it) {
                viewModel.getBook(Parser.icons[0].url!!)
            }
        }
        viewModel.liveData.observe(this) {
            vb.refresh.isRefreshing = false
            bliViewModel.showProgress
            if (it.size > 0)
                adapter.setData(it)
        }
        viewModel.showProgress.observe(this){
            if(it){
                showProgress()
            }else{
                closeProgress()
            }
        }

    }

}