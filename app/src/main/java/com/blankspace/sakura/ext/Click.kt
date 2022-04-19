package com.blankspace.sakura.ext

import android.view.View

fun onClick(vararg views: View?, onClick: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener { view ->
            onClick(view)
        }
    }
}