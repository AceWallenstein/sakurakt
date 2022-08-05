package com.blankspace.sakura.drag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.blankspace.sakura.adapter.DragAdapter
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.databinding.FragmentDragBinding
import com.blankspace.sakura.widget.DragCallBack

class DragFragment : BaseFragment<FragmentDragBinding>() {

    private val mList = mutableListOf<String>("C++","python","maya","flash","kiki")
    private val mAdapter:DragAdapter by lazy(LazyThreadSafetyMode.NONE){
        DragAdapter(requireContext(),mList)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDragBinding = FragmentDragBinding.inflate(inflater)

    override fun initView(vb: FragmentDragBinding) {
        vb.run {
            recyclerView.run {
                adapter = mAdapter
                layoutManager = GridLayoutManager(context,4)
            }
            val dragCallBack = DragCallBack(mAdapter, mList)
            val itemTouchHelper = ItemTouchHelper(dragCallBack)
            itemTouchHelper.attachToRecyclerView(recyclerView)

        }
    }

}