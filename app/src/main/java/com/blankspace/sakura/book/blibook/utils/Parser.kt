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


interface CallBack {
    fun onSucceed(type:Int)

}

object Parser {
    private const val TAG = "parser"
    public val icons = mutableListOf<Icon>(
        Icon("icon-fuli"),
        Icon("icon-rank"),
        Icon("icon-newbook"),
        Icon("icon-end")
    )
    private val cssUrls = mutableListOf<String>()
    private val patterncss2 = "<link rel=\"stylesheet\" href=\"(.*?)\""
    private val pattern_href = "href=\"(.*?)\""


    public suspend fun parse(html: String, callBack: CallBack) {
        val soup = Jsoup.parse(html)
        val e_icons = soup.select(".icon")
        val css_urls = patterncss2.toRegex().findAll(html)
        css_urls.forEach { it ->
            Log.d(TAG, "parse: css_urls ${it.value}")
            val find = pattern_href.toRegex().find(it.value)!!.groups[1]?.value
            Log.d(TAG, "parse: find$find")
            find?.let {
                cssUrls.add(find)
            }
            e_icons.forEach { it ->
                Log.d(TAG, "parse: ${it.className().split(" ")[1]}")
                Log.d(TAG, "parse: parent：${it.parent()?.attr("href")}")
                icons.forEach { icon ->
                    if (icon.name == it.className().split(" ")[1]){
                        Log.d(TAG, "href: ${it.parent()?.attr("href")}")
                        val href = it.parent()?.attr("href")
                        icon.url = href
                    }
                }
            }
        }
        callBack.onSucceed(1)
        CoroutineScope(Dispatchers.IO).launch {
            parseCss(callBack)
            callBack.onSucceed(0)
        }


    }

    private var sprite_css: String = ""
    private var pic_base_url: String = "https://w.linovelib.com/themes/zhmb/"
    private val rex = "(.*?)[)];"
    private val pic_rex = "\\{background-image:url[(]../(.*?)[)];"
    private val name_rex = "(.*?)\\{"
    suspend fun parseCss(callBack: CallBack) {
        cssUrls.forEach {
            try {
                val parseCss = RetrofitClient.bookApiService.parseCss(it)
                icons.forEach { icon ->
                    val matches = icon.name
                    if (matches.toRegex().containsMatchIn(parseCss)) {
                        sprite_css = parseCss
                    }
//                if () {
//                    sprite_css = parseCss
//                    return@forEach
//                }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    toast(App.instance, e.message ?: "")
                }
            }
        }
        icons.forEach {
            if (sprite_css.isNotBlank()) {
                val findAll = (it.name + rex).toRegex().findAll(sprite_css)
                findAll.forEach { result ->
                    Log.d(TAG, "parseCss: sprite_css${result.value}")
                    val pic_url = pic_rex.toRegex().find(result.value)!!.groups[1]
                    val name = name_rex.toRegex().find(result.value)!!.groups[1]
                    if (it.name == name?.value) {
                        if (pic_url?.value!!.contains("@2x")) {
                            it.pic_url = pic_base_url + pic_url.value
                            Log.d(TAG, "parseCss: pic_url ${it.pic_url}")
                        }

                    }
                }

            }

        }


    }

}