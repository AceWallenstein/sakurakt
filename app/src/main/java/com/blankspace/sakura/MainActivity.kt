package com.blankspace.sakura

import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.blankspace.sakura.adapter.FragmentTabAdapter
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.common.utils.NavigationUtil
import com.blankspace.sakura.databinding.ActivityMainBinding
import com.blankspace.sakura.ext.onClick
import com.blankspace.sakura.ext.textColor
import com.blankspace.sakura.form.FormFragment
import com.blankspace.sakura.home.HomeFragment


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val fragments: MutableList<Fragment> = arrayListOf()
    private val tabText = arrayOf("首页", "课程", "论坛", "我的")

    private val normalIcon = intArrayOf(
        R.drawable.tab_home, R.drawable.tab_class,
        R.drawable.tab_forum, R.drawable.tab_mine
    )
    private val selectIcon = intArrayOf(
        R.drawable.tab_home_1, R.drawable.tab_class_1,
        R.drawable.tab_forum_1, R.drawable.tab_mine_1
    )

    private var msgPointMoreWidth = 0
    private var msgPointMoreHeight: Int = 0
    private var msgPointMoreRadius: Int = 0
    private var msgPointColor: Int = 0
    private var msgPointTextSize: Int = 0
    private var msgPointSize: Int = 0

    //    @BindView(R.id.navigation)
    //    EasyNavigationBar navigationBar;
    public var mLeft = 0
    public var mTop = 0
    public var isFirst = false


    private lateinit var tabAdapter: FragmentTabAdapter

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun initView() {
        initFragments()
        initTab()
        selectTab(0)
        initRedPoint()
        setMsgPointCount(vb.msgPointTv, 1)
    }

    private fun initFragments() {
        fragments.add(HomeFragment())
        fragments.add(FormFragment())
        fragments.add(FormFragment())
        fragments.add(HomeFragment())
//        fragments.plus(ClassFragment())
//        fragments.plus(FormFragment())
//        fragments.plus(MineFragment())
        tabAdapter = FragmentTabAdapter(this, fragments, R.id.fl_layout)
    }

    override fun onViewClick() {
        vb.apply {
            onClick(llHome, llClass, llForum, llMine) {
                when (it.id) {
                    R.id.ll_home -> {
                        selectTab(0)
                    }
                    R.id.ll_class -> {
                        selectTab(1)
                    }
                    R.id.ll_forum -> {
                        selectTab(2)
                    }
                    R.id.ll_mine -> {
                        selectTab(3)
                    }
                }
            }
        }
    }

    private fun initTab() {
        vb.apply {

            ivHome.setImageResource(normalIcon[0])
            ivClass.setImageResource(normalIcon[1])
            ivForum.setImageResource(normalIcon[2])
            ivMine.setImageResource(normalIcon[3])
            tvHome.textColor = 0xff666666
            tvClass.textColor = 0xff666666
            tvForum.textColor = 0xff666666
            tvMine.textColor = 0xff666666
        }
    }


    private fun selectTab(pos: Int) {
        initTab()
//                viewPager.setCurrentItem(pos);
        tabAdapter.setCurrentFragment(pos)
        vb.apply {
            when (pos) {
                0 -> {
                    ivHome.setImageResource(selectIcon[pos])
                    tvHome.setTextColor(-0x9b8306)
                }
                1 -> {
                    ivClass.setImageResource(selectIcon[pos])
                    tvClass.setTextColor(-0x9b8306)
                }
                2 -> {
                    ivForum.setImageResource(selectIcon[pos])
                    tvForum.setTextColor(-0x9b8306)
                }
                3 -> {
                    ivMine.setImageResource(selectIcon[pos])
                    tvMine.setTextColor(-0x9b8306)
                }
            }
        }
    }

    private fun initRedPoint() {
        vb.msgPointTv.setTextSize(1, 11f)
        val msgPointParams = vb.msgPointTv.getLayoutParams() as RelativeLayout.LayoutParams
        //        msgPointParams.bottomMargin = (int)  2;
//        msgPointParams.leftMargin = (int) 2;
        vb.msgPointTv.setLayoutParams(msgPointParams)
        setMsgPointCount(vb.msgPointTv, 1000)
        msgPointMoreWidth = NavigationUtil.dip2px(mContext, 30f)
        //消息红点99+的高度
        msgPointMoreHeight = NavigationUtil.dip2px(mContext, 16f)
        //消息红点99+的半径
        msgPointMoreRadius = 10
        //消息红点颜色
        msgPointColor = Color.parseColor("#ff0000")
        //消息红点字体大小
        msgPointTextSize = 11
        //消息红点大小
        msgPointSize = NavigationUtil.dip2px(mContext, 16f)
    }

    fun setMsgPointCount(msgPointView: TextView, count: Int) {
        if (count > 99) {
            NavigationUtil.setRoundRectBg(mContext, msgPointView, msgPointMoreRadius, msgPointColor)
            msgPointView.text = "99+"
            val params = msgPointView.layoutParams
            params.width = msgPointMoreWidth
            params.height = msgPointMoreHeight
            msgPointView.layoutParams = params
            msgPointView.visibility = View.VISIBLE
        } else if (count < 1) {
            msgPointView.visibility = View.GONE
        } else {
            val params = msgPointView.layoutParams
            params.width = msgPointSize
            params.height = msgPointSize
            msgPointView.layoutParams = params
            NavigationUtil.setOvalBg(msgPointView, msgPointColor)
            msgPointView.text = count.toString() + ""
            msgPointView.visibility = View.VISIBLE
        }
    }


}