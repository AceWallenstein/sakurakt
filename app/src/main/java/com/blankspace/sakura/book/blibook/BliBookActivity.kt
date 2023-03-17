package com.blankspace.sakura.book.blibook

import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.book.blibook.home.QuanZiFragment
import com.blankspace.sakura.book.blibook.test.TestFragment
import com.blankspace.sakura.book.blibook.utils.Parser
import com.blankspace.sakura.databinding.ActivityBlibookBinding
import com.blankspace.sakura.home.HomeFragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BliBookActivity : BaseActivity<ActivityBlibookBinding>() {
    private val TAG = "BliBookActivity"
    private val fragments = mutableListOf<Fragment>(
        QuanZiFragment(),
        TestFragment(),
        HomeFragment(),
        HomeFragment()
    )

    private val bliViewModel by viewModels<BliViewModel>()

    override fun getViewBinding(): ActivityBlibookBinding =
        ActivityBlibookBinding.inflate(layoutInflater)

    override fun initView() {
//        initNavigation()
        bliViewModel.loadImage.observe(this@BliBookActivity) {
            if (it) {
                initNavigation()
            }
        }
        bliViewModel.showProgress.observe(this){
            if(it){
                showProgress()
            }else{
                closeProgress()
            }
        }
    }

    override fun initData() {
        bliViewModel.launchHomePage()
    }

    private val menu_text = listOf<String>( "文库", "排行榜", "标签", "完本")
    private fun initViewPager() {
        with(vb) {
            viewPager.isUserInputEnabled = false
            val homePagerAdapter = HomePagerAdapter(this@BliBookActivity, fragments = fragments)
            viewPager.adapter = homePagerAdapter
            navigation.setOnItemSelectedListener { item ->
                viewPager.currentItem = item.itemId
                true
            }
        }
    }

    private fun initNavigation() {
        with(vb) {
            navigation.menu.clear()
            Parser.icons.forEachIndexed { index, icon ->
                navigation.menu.add(0, index, 0, menu_text[index])
            }
            val menuView = navigation.getChildAt(0) as BottomNavigationMenuView
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                val imageView: ImageView =
                    item.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view)
                Log.d(TAG, "initNavigation: ${Parser.icons[i].pic_url}")
                Glide.with(this@BliBookActivity)
                    .load(Parser.icons[i].pic_url).into(imageView)

            }
            initViewPager()
//            navigation.menu.forEachIndexed { index, icon ->
//                Glide.with(this@BliBookActivity)
//                    .asBitmap()
//                    .load(Parser.icons[index].pic_url).into(object : SimpleTarget<Bitmap>() {
//                        override fun onResourceReady(
//                            resource: Bitmap,
//                            transition: Transition<in Bitmap>?
//                        ) {
//                            icon.icon = BitmapDrawable(resource)
//                        }
//
//                    })
//            }

        }
    }

    class HomePagerAdapter(fragmentActivity: FragmentActivity, val fragments: List<Fragment>) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragments.size;
        }

        override fun createFragment(position: Int): Fragment {
            return fragments.get(position);
        }
    }

}