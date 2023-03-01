package com.blankspace.sakura.book.blibook.book

import android.net.Uri
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blankspace.sakura.R
import com.blankspace.sakura.book.blibook.model.Book
import com.blankspace.sakura.common.utils.Logger
import com.blankspace.sakura.common.utils.whiteHostList
import com.blankspace.sakura.form.DetailViewModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient


class WebBookActivity : AppCompatActivity(R.layout.activity_detail) {

    companion object {
        const val PARAM_ARTICLE = "book"
        const val BASE_URL = "https://w.linovelib.com"
    }

    private lateinit var book: Book

    private var agentWeb: AgentWeb? = null

    private val tvTitle by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.tvTitle) }
    private val ivBack by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.ivBack) }
    private val ivMore by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.ivMore) }
    private val webContainer by lazy(LazyThreadSafetyMode.NONE) { findViewById<FrameLayout>(R.id.webContainer) }

    private val mViewModel by viewModels<DetailViewModel>()

    override fun onStart() {
        super.onStart()
        initView()
        initData()
    }


    fun initView() {

        book = intent?.getParcelableExtra(PARAM_ARTICLE) ?: return

        setDetailTitle(book.title)

        ivBack.setOnClickListener {
            if (agentWeb != null) {
                if (!agentWeb!!.back()) {
                    finish()
                }
            } else {
                finish()
            }
//            ActivityHelper.finish(DetailActivity::class.java)
        }
        ivMore.setOnClickListener {
            agentWeb?.webCreator?.webView?.loadUrl(customJs())

        }

    }

    fun initData() {
//        if (article.id != 0) {
//            mViewModel.saveReadHistory(article)
//        }
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(webContainer, LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.textColorPrimary), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.include_reload, R.id.btnReload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(webChromeClient)
            .setWebViewClient(webViewClient)
            .createAgentWeb()
            .ready()
            .get()
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
                textZoom = 100
            }
        }
        val href = book.url.substring(0, book.url.lastIndexOf(".")) + "/catalog"
        agentWeb?.urlLoader?.loadUrl(BASE_URL + href)
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            setDetailTitle(title, true)
            super.onReceivedTitle(view, title)
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            Logger.d("WanAandroidWebView", consoleMessage?.message())
            return super.onConsoleMessage(consoleMessage)
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return !whiteHostList().contains(request?.url?.host)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view?.loadUrl(customJs(url))
        }
    }

    /**
     * 加载js，去掉掘金、简书、CSDN等H5页面的Title、底部操作栏，以及部分广告
     */
    private fun customJs(url: String? = book.url): String {
        val js = StringBuilder()
        js.append("javascript:(function(){")
        when (Uri.parse(url).host) {
            "juejin.im", "juejin.cn" -> {
                js.append("var followButtonList = document.getElementsByClassName('follow-button');")
                js.append("if(followButtonList&&followButtonList.length){followButtonList[0].parentNode.removeChild(followButtonList[0])}")
                js.append("var articleBannerList = document.getElementsByClassName('article-banner');")
                js.append("if(articleBannerList&&articleBannerList.length){articleBannerList[0].parentNode.removeChild(articleBannerList[0])}")
                js.append("var subscribeBtnList = document.getElementsByClassName('subscribe-btn');")
                js.append("if(subscribeBtnList&&subscribeBtnList.length){subscribeBtnList[0].parentNode.removeChild(subscribeBtnList[0])}")
                js.append("var headerList = document.getElementsByClassName('main-header-box');")
                js.append("if(headerList&&headerList.length){headerList[0].parentNode.removeChild(headerList[0])}")
                js.append("var openAppList = document.getElementsByClassName('open-in-app');")
                js.append("if(openAppList&&openAppList.length){openAppList[0].parentNode.removeChild(openAppList[0])}")
                js.append("var actionBox = document.getElementsByClassName('action-box');")
                js.append("if(actionBox&&actionBox.length){actionBox[0].parentNode.removeChild(actionBox[0])}")
                js.append("var actionBarList = document.getElementsByClassName('action-bar');")
                js.append("if(actionBarList&&actionBarList.length){actionBarList[0].parentNode.removeChild(actionBarList[0])}")
                js.append("var columnViewList = document.getElementsByClassName('column-view');")
                js.append("if(columnViewList&&columnViewList.length){columnViewList[0].style.margin = '0px'}")
            }
            "www.jianshu.com" -> {
                js.append("var badgeItemList = document.getElementsByClassName('badge-item');")
                js.append("if(badgeItemList&&badgeItemList.length){badgeItemList[0].parentNode.removeChild(badgeItemList[0])}")
                js.append("var appOpenList = document.getElementsByClassName('app-open');")
                js.append("if(appOpenList&&appOpenList.length){appOpenList[0].parentNode.removeChild(appOpenList[0])}")
                js.append("var jianshuHeader = document.getElementById('jianshu-header');")
                js.append("if(jianshuHeader){jianshuHeader.parentNode.removeChild(jianshuHeader)}")
                js.append("var commentMain = document.getElementById('comment-main');")
                js.append("if(commentMain){commentMain.parentNode.removeChild(commentMain)}")
                js.append("var footer = document.getElementById('footer');")
                js.append("if(footer){footer.parentNode.removeChild(footer)}")
                js.append("var revealAd = document.getElementById('reveal-ad');")
                js.append("if(revealAd){revealAd.parentNode.removeChild(revealAd)}")
                js.append("var headerShimList = document.getElementsByClassName('header-shim');")
                js.append("if(headerShimList&&headerShimList.length){headerShimList[0].parentNode.removeChild(headerShimList[0])}")
                js.append("var fubiaoList = document.getElementsByClassName('fubiao-dialog');")
                js.append("if(fubiaoList&&fubiaoList.length){fubiaoList[0].parentNode.removeChild(fubiaoList[0])}")
                js.append("var ads = document.getElementsByClassName('note-comment-above-ad-wrap');")
                js.append("if(ads&&ads.length){ads[0].parentNode.removeChild(ads[0])}")
                js.append("var lazyShimList = document.getElementsByClassName('v-lazy-shim');")
                js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[0]){lazyShimList[0].parentNode.removeChild(lazyShimList[0])}")
                js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[1]){lazyShimList[1].parentNode.removeChild(lazyShimList[1])}")
                js.append("var callAppBtnList = document.getElementsByClassName('call-app-btn');")
                js.append("if(callAppBtnList&&callAppBtnList.length){callAppBtnList[0].parentNode.removeChild(callAppBtnList[0])}")
            }
            "blog.csdn.net" -> {
                js.append("var detailFollow = document.getElementById('detailFollow');")
                js.append("if(detailFollow){detailFollow.parentNode.removeChild(detailFollow)}")
                js.append("var csdnToolBar = document.getElementById('csdn-toolbar');")
                js.append("if(csdnToolBar){csdnToolBar.parentNode.removeChild(csdnToolBar)}")
                js.append("var csdnMain = document.getElementById('main');")
                js.append("if(csdnMain){csdnMain.style.margin='0px'}")
                js.append("var operate = document.getElementById('operate');")
                js.append("if(operate){operate.parentNode.removeChild(operate)}")
                js.append("var haveHeartCountList = document.getElementsByClassName('have-heart-count');")
                js.append("if(haveHeartCountList&&haveHeartCountList.length){haveHeartCountList[0].parentNode.removeChild(haveHeartCountList[0])}")
                js.append("var asideHeaderFixedList = document.getElementsByClassName('aside-header-fixed');")
                js.append("if(asideHeaderFixedList&&asideHeaderFixedList.length){asideHeaderFixedList[0].parentNode.removeChild(asideHeaderFixedList[0])}")
                js.append("var feedSignSpanList = document.getElementsByClassName('feed-Sign-span');")
                js.append("if(feedSignSpanList&&feedSignSpanList.length){feedSignSpanList[0].parentNode.removeChild(feedSignSpanList[0])}")
            }
            "w.linovelib.com" -> {
                js.append("var aswift_1_host = document.getElementById('aswift_1_host');")
                js.append("if(aswift_1_host){aswift_1_host.parentNode.removeChild(aswift_1_host)}")
                js.append("var ad_unit = document.getElementById('ad_unit');")
                js.append("if(ad_unit){ad_unit.parentNode.removeChild(ad_unit)}")
                js.append("var google_esf = document.getElementById('google_esf');")
                js.append("if(google_esf){google_esf.parentNode.removeChild(google_esf)}")
                js.append("var aswift_2_host = document.getElementById('aswift_2_host');")
                js.append("if(aswift_2_host){aswift_2_host.parentNode.removeChild(aswift_2_host)}")
                js.append("var aswift_2_host = document.getElementById('aswift_2_host');")
                js.append("if(aswift_2_host){aswift_2_host.parentNode.removeChild(aswift_2_host)}")
                js.append("javascript:(function(){let iframes=document.getElementsByTagName(\"iframe\");for(let i=0;i<iframes.length;i++){iframes[i].style.display=\"none\";iframes[i].style.visibility=\"hidden\";}})();\n")
                js.append("var adsbygoogle = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate');")
                js.append("for (var i=0;i<adsbygoogle.length;i++){  if(adsbygoogle[i]){adsbygoogle[i].parentNode.removeChild(adsbygoogle[i])}}")
                js.append("var ap_container = document.getElementsByClassName('google-auto-placed ap_container');")
                js.append("for (var i=0;i<ap_container.length;i++){  if(ap_container[i]){ap_container[i].parentNode.removeChild(ap_container[i])}}")


            }
        }
        js.append("})()")
        return js.toString()
    }

    /**
     * 设置标题
     */
    private fun setDetailTitle(title: CharSequence?, isSelected: Boolean = false) {
        tvTitle.text = title
        tvTitle.postDelayed({ tvTitle.isSelected = isSelected }, 500)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event) == true) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()

    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }


    fun refreshPage() {
        agentWeb?.urlLoader?.reload()
    }


}
