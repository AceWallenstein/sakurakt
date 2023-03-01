package com.blankspace.sakura.book.blibook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(var url:String,val title:String,val des:String,val cover:String,val author:String) :
    Parcelable