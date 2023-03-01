package com.blankspace.sakura.book.blibook.model

data class Pager(val total: String, val chapter: List<Chapter>) {
}

data class Chapter(val title: String, val index: List<Index>)

data class Index(val itemTitle: String,val url:String)