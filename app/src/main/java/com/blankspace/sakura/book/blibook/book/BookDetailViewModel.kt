package com.blankspace.sakura.book.blibook.book

import android.util.Log
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.book.blibook.model.Chapter
import com.blankspace.sakura.book.blibook.model.Index
import com.blankspace.sakura.book.blibook.model.Pager
import com.blankspace.sakura.net.RetrofitClient
import org.jsoup.Jsoup

class BookDetailViewModel : BaseViewModel() {

    val chapters: ArrayList<Chapter> = arrayListOf()
    fun parse(pageUrl: String) {
        launch({
            val bookIndex = RetrofitClient.bookApiService.getBookIndex(pageUrl)
            val parse = Jsoup.parse(bookIndex)
            val pager = parse.getElementById("volumes")
            pager?.let {
                for (i in 0 until pager.childrenSize()) {
                    val name = pager.child(i).className()
                    Log.d("parse", "$i   parse:   $name")
                    if (pager.child(i).className().equals("chapter-bar chapter-li")) {
                        val indexs = mutableListOf<Index>()
                        val chapterName = pager.child(i).text()
                        for (j in i+1 until pager.childrenSize()) {
                            if (pager.child(j).className().equals("chapter-li jsChapter")) {
                                val title = pager.child(j).child(0).child(0).text()
                                val url = pager.child(j).child(0).attr("href")
                                val index = Index(title, url)
                                indexs.add(index)
                            } else if (pager.child(j).className()
                                    .equals("chapter-bar chapter-li")
                            ) {
                                val chapter = Chapter(chapterName, indexs)
                                chapters.add(chapter)
                                break
                            }
                        }
                    }
                }
            }
            val total = parse.select("h4.chapter-sub-title")[0].child(0).text()
            val pager1 = Pager(total, chapters)
        })
    }
}