package com.blankspace.sakura.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout

class NestCollapsingToolbarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? =null,
    defStyleAttr: Int = 0
) : CollapsingToolbarLayout(
    context,
    attrs,
    defStyleAttr
) {

    private var mIsScrimsShown: Boolean = false
    private var scrimsShowListener: OnScrimsShowListener? = null


    override fun setScrimsShown(shown: Boolean, animate: Boolean) {
        super.setScrimsShown(shown, animate)
        if (mIsScrimsShown != shown) {
            mIsScrimsShown = shown
            scrimsShowListener?.onScrimsShowChange(this, mIsScrimsShown)

        }
    }

    fun setOnScrimesShowListener(onScrimsShowListener: OnScrimsShowListener) {
        this.scrimsShowListener = onScrimsShowListener
    }

    interface OnScrimsShowListener {
        fun onScrimsShowChange(
            nestCollapsingToolbarLayout: NestCollapsingToolbarLayout,
            mIsScrimsShown: Boolean
        )

    }
}