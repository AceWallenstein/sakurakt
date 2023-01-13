package com.blankspace.sakura.book.blibook.utils

import android.util.Log
import com.blankspace.sakura.App
import com.blankspace.sakura.book.blibook.model.Icon
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.net.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

object Parser {
    private const val TAG = "parser"
    private val icons = mutableListOf<Icon>()
    private val cssUrls = mutableListOf<String>()
    private val patterncss2 = "<link rel=\"stylesheet\" href=\"(.*?)\""
    private val pattern_href = "href=\"(.*?)\""


    public suspend fun parse(html: String) {
        val soup = Jsoup.parse(html)
        val e_icons = soup.select(".icon")
        val css_urls = patterncss2.toRegex().findAll(html)
        css_urls.forEach {
            Log.d(TAG, "parse: css_urls ${it.value}")
            val find = pattern_href.toRegex().find(it.value)!!.groups[1]?.value
            Log.d(TAG, "parse: find$find")
            find?.let {
                cssUrls.add(find)
            }
            e_icons.forEach {
                Log.d(TAG, "parse: ${it.className()}")
                val icon = Icon(it.className())
                icons.add(icon)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            parseCss()
        }


    }

    var sprite_css: String = ""
    val rex = "(.*?)[)];"
    suspend fun parseCss() {
        cssUrls.forEach { it ->
            try {
                val parseCss = RetrofitClient.bookApiService.parseCss(it)
                icons.forEach {
                    val matches = it.name.split(" ")[1]
                    if (matches.toRegex().containsMatchIn(parseCss)) {
                        sprite_css = parseCss
                    }
//                if () {
//                    sprite_css = parseCss
//                    return@forEach
//                }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    toast(App.instance,e.message?:"")
                }
            }
        }
        icons.forEach {
            if (sprite_css.isNotBlank()) {
                val findAll = (it.name.split(" ")[1] + rex).toRegex().findAll(sprite_css)
                findAll.forEach {
                    Log.d(TAG, "parseCss: sprite_css${it.value}")
                }
            }

        }

    }

}