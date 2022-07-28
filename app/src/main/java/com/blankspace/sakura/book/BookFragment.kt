package com.blankspace.sakura.book

import android.content.Intent
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.databinding.FragmentBookBinding

class BookFragment : BaseFragment<FragmentBookBinding>(){
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookBinding = FragmentBookBinding.inflate(layoutInflater)

    override fun initView(vb: FragmentBookBinding) {
        setHasOptionsMenu(true)
        val parent = activity as AppCompatActivity
        parent.setSupportActionBar(vb.toolBar)


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