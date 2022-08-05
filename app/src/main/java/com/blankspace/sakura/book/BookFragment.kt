package com.blankspace.sakura.book

import android.content.Intent
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blankspace.sakura.R
import com.blankspace.sakura.adapter.BookAdapter
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.data.BookRepository
import com.blankspace.sakura.data.room.AppDataBase
import com.blankspace.sakura.databinding.FragmentBookBinding
import com.blankspace.sakura.widget.StaggeredDividerItemDecoration
import kotlinx.coroutines.flow.collectLatest

class BookFragment : BaseFragment<FragmentBookBinding>(){
    lateinit var mAdapter: BookAdapter

    val viewModel by viewModels<BookListViewModel> {BookListViewModelFactory(BookRepository.getInstance(
        AppDataBase.getInstance(requireContext()).getBookDao()))  }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookBinding = FragmentBookBinding.inflate(layoutInflater)

    override fun initView(vb: FragmentBookBinding) {
        setHasOptionsMenu(true)
        val parent = activity as AppCompatActivity
        parent.setSupportActionBar(vb.toolBar)
        with(vb){
            mAdapter = BookAdapter{
                _,book,_->
                    toast(requireContext(), book.name)

            }
            recyclerView.adapter = mAdapter
            recyclerView.layoutManager = GridLayoutManager(context,2)
            recyclerView.addItemDecoration(StaggeredDividerItemDecoration(requireContext()))

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.bookFlow.collectLatest {
                mAdapter.submitData(it)
            }
        }



    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.book_list,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_book_list_add){
            startActivity(Intent(context,ScanBookActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}