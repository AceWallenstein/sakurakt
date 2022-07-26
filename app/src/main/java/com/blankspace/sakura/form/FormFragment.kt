package com.blankspace.sakura.form

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.databinding.FragmentFormBinding
import com.blankspace.sakura.ext.onClick
import com.google.android.material.tabs.TabLayoutMediator

class FormFragment : BaseFragment<FragmentFormBinding>() {
    val titles = listOf<String>(
        "zhang",
        "phil",
        "zhang phil",
        "csdn",
        "zhang phil csdn",
        "zhang phil @ csdn",
        "blog.csdn.net/zhangphil",
        "android"
    )
    private val fragments: MutableList<Fragment> = arrayListOf()
    private lateinit var tabAdapter: FragmentStateAdapter


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFormBinding {
        return FragmentFormBinding.inflate(inflater, container, false)
    }

    override fun initView(vb: FragmentFormBinding) {
        with(vb) {
            onClick(ivDrag) {
                toast(content = "dianji")
            }
            initFragments()
//            tabLayout.setTitle(titles)
            viewPager.adapter = tabAdapter
//            viewPager.offscreenPageLimit = fragments.size
            TabLayoutMediator(tabLayout,viewPager){tab, position ->
//                tab.setIcon(getIcon(position))
                tab.setCustomView(R.layout.tablayout_item)
                val tab_text = tab.customView!!.findViewById<TextView>(R.id.tab_text)
                tab_text.setText(titles[position])
//                tab.text = titles[position]


            }.attach()
//            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//                override fun onTabSelected(tab: TabLayout.Tab?) {
//                    viewPager.setCurrentItem(tab?.position!!, false)
//                }
//
//                override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//                }
//
//                override fun onTabReselected(tab: TabLayout.Tab?) {
//
//                }
//
//            })
//            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    tabLayout.setScrollPosition(position, 0f, false)
//                    tabLayout.getTabAt(position)?.select();
//
//                }
//            })
        }
    }

    private fun initFragments() {
        fragments.add(OneFragment.newInstance(1))
        fragments.add(OneFragment.newInstance(2))
        fragments.add(OneFragment.newInstance(3))

        tabAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }
    }


}