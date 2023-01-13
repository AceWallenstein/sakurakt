package com.blankspace.sakura.book.blibook.utils

import android.util.Log
import com.blankspace.sakura.book.blibook.model.Icon
import org.jsoup.Jsoup

object Parser {
    private const val TAG = "parser"
    private val icons = mutableListOf<Icon>()
    private val cssUrls = mutableListOf<String>()
    private val patterncss2 = "<link rel=\"stylesheet\" href=\"(.*?)\""
    private val pattern_href = "href=\"(.*?)\""


    public fun parse(html: String) {
        val soup = Jsoup.parse(html)
        val e_icons = soup.select(".icon")
        val css_urls = patterncss2.toRegex().findAll(html)
        css_urls.forEach {
            Log.d(TAG, "parse: css_urls ${it.value}")
            val find = pattern_href.toRegex().find(it.value)?.value
            find?.let {
                cssUrls.add(find)
            }

        }
        e_icons.forEach {
            Log.d(TAG, "parse: ${it.className()}")
            val icon = Icon(it.className())
            icons.add(icon)
        }

    }

}