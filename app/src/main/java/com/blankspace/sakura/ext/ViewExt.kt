package com.blankspace.sakura.ext

import android.widget.TextView

var TextView.textColor: Long
    get() {
        //... not important
        return this.textColor
    }
    set(value: Long) {
        this.setTextColor(value.toInt())
    }