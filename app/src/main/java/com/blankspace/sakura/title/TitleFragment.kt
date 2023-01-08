package com.blankspace.sakura.title

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.databinding.FragmentTitleBinding
import com.blankspace.sakura.widget.NestCollapsingToolbarLayout

class TitleFragment : BaseFragment<FragmentTitleBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTitleBinding = FragmentTitleBinding.inflate(inflater)

    override fun initView(vb: FragmentTitleBinding) {
        with(vb){
            toolbarLayout.setOnScrimesShowListener(object :
                NestCollapsingToolbarLayout.OnScrimsShowListener {
                override fun onScrimsShowChange(
                    nestCollapsingToolbarLayout: NestCollapsingToolbarLayout,
                    isScrimesShow: Boolean
                ) {
                    if (isScrimesShow) {
                        rlTitle.setBackgroundColor(Color.WHITE)
                        ivBack.setImageResource(R.drawable.ic_back_blue)
                        ivShare.setImageResource(R.drawable.ic_share_blue)
                        ivUserData.setImageDrawable(
                            tintDrawable(
                                resources.getDrawable(R.drawable.ic_userdata),
                                ColorStateList.valueOf(Color.parseColor("#B6B6B6"))
                            )
                        )
//                        showSmallAuthor()
                    } else {
                        rlTitle.setBackgroundColor(Color.TRANSPARENT)
                        ivBack.setImageResource(R.drawable.ic_back)
                        ivShare.setImageResource(R.drawable.ic_share)
                        ivUserData.setImageDrawable(
                            tintDrawable(
                                ivUserData.drawable,
                                ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                            )
                        )
//                        if (objectAnimator.isRunning) {
//                            objectAnimator.cancel()
//                        }
                        llSmallAuthor.visibility = View.INVISIBLE
                    }
                }

            })
        }
    }

    fun tintDrawable(drawable: Drawable, colors: ColorStateList): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(wrappedDrawable, colors)
        return wrappedDrawable
    }
}