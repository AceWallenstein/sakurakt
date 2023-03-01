package com.blankspace.sakura.lottery;

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.R
import com.blankspace.sakura.adapter.BaseAdapter
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.databinding.ActivityLotteryBinding
import com.blankspace.sakura.ext.dp
import com.blankspace.sakura.ext.onClick
import kotlin.math.hypot

public class LotteryActivity : BaseActivity<ActivityLotteryBinding>() {

    override fun getViewBinding(): ActivityLotteryBinding {
        return ActivityLotteryBinding.inflate(getLayoutInflater());
    }

    val pLeft = 1f;
    val pTop = 1f;
    val pBottom = 1f;
    val pRight = 1f

    var flag = true

    override fun initView() {
        val recyclerView = vb.recyclerView;
        with(recyclerView) {
            layoutManager = LoopLayoutManager()
            val l = LinearLayoutManager(mContext)
            adapter =
                MyAdapter().also { it.setData(arrayListOf("1", "2", "3", "4", "5", "7", "8")) }
        }
        recyclerView.postDelayed(runnable, 10)

        with(vb) {
            onClick(tvAdd, llAddAccount,view) {
                when (it) {
                    llAddAccount -> {
                        if (flag) {
                            val animator =
                                ObjectAnimator.ofFloat(llAddAccount, "translationX", 0f, -(70f.dp))
                            animator.start()
                        } else {
                            val animator =
                                ObjectAnimator.ofFloat(llAddAccount, "translationX", -(70f.dp), 0f)
                            animator.start()
                        }
                        flag = !flag

                    }
                    tvAdd -> toast(content = "add !")
                    view -> showCircleAnim(vb)
                }
            }
        }
    }


    private fun showCircleAnim(binding: ActivityLotteryBinding) {
        val finalRadius = hypot(binding.view.getWidth().toFloat(),
            binding.view.getHeight().toFloat()
        ) as Float;
        val anim = ViewAnimationUtils.createCircularReveal(binding.view, 0, 0, 0f, finalRadius);
        binding.view.setVisibility(View.VISIBLE);
        anim.setDuration(800);
        anim.start();
    }


    private val runnable = object : Runnable {
        override fun run() {
            vb.recyclerView.scrollBy(3, 0)
            vb.recyclerView.postDelayed(this, 10)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.recyclerView.removeCallbacks(runnable)
    }

    class MyAdapter : BaseAdapter<String, MyAdapter.VH>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return VH(view)
        }


        override fun onBindViewHolder(holder: VH, position: Int) {
            if (position % 2 == 0) {
                holder.imageView.setBackgroundColor(Color.YELLOW)
            } else {
                holder.imageView.setBackgroundColor(Color.BLUE)
            }
            holder.imageView.setOnClickListener({
                toast(content = "$position  ${mDatas?.get(position)}")
            })


        }

        class VH(val itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView = itemView.findViewById<ImageView>(R.id.image)

        }
    }


}