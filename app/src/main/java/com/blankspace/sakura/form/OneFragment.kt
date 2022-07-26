package com.blankspace.sakura.form

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blankspace.sakura.adapter.PicAdapter
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.bean.Course
import com.blankspace.sakura.book.WebBookActivity
import com.blankspace.sakura.databinding.FragmentOneBinding
import com.blankspace.sakura.net.RetrofitClient
import com.blankspace.sakura.widget.StaggeredDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OneFragment : BaseFragment<FragmentOneBinding>() {
    private val picAdapter: PicAdapter by lazy {
        PicAdapter(listener = { _: View, course: Course, pos: Int ->
            val intent = Intent(context, WebBookActivity::class.java)
            intent.putExtra("cid",course.id)
            startActivity(intent)
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOneBinding {
        return FragmentOneBinding.inflate(inflater, container, false)

    }

    override fun initView(vb: FragmentOneBinding) {
        vb.apply {
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(StaggeredDividerItemDecoration(context))
                adapter = picAdapter
            }
        }
        getCourse()
    }

    companion object {
        @JvmStatic
        fun newInstance(one: Int): OneFragment {
            val args = Bundle()
            args.putInt("one", one)
            val fragment = OneFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun getCourse() {
        CoroutineScope(lifecycleScope.coroutineContext).launch {
            val course = RetrofitClient.apiService.getCourse().apiData() as MutableList<Course>
            picAdapter.setData(course)
        }
    }
}