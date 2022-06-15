package com.blankspace.sakura.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.blankspace.sakura.adapter.DayAdapter
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.common.utils.CalendarUtil
import com.blankspace.sakura.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {
    lateinit var mAdapter: DayAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(inflater, container, false)
    }

    private val showYear = 0
    private val showMonth = 0
    private var firstDayInMonth = 0
    override fun initView(vb: FragmentCalendarBinding) {
        mAdapter = DayAdapter()
        with(vb){
            recyclerView.run {
                layoutManager = GridLayoutManager(context,7)
                adapter = mAdapter
            }
        }
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val days = CalendarUtil.getDay(y, m)
        mAdapter.setData(days)

    }

}