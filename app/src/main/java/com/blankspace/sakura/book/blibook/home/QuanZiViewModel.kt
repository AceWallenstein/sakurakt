package com.blankspace.sakura.book.blibook.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.book.blibook.model.Book
import com.blankspace.sakura.book.blibook.utils.CallBack
import com.blankspace.sakura.net.RetrofitClient
import org.jsoup.Jsoup

class QuanZiViewModel : BaseViewModel() {
    val showProgress: LiveData<Boolean>
        get() = _showProgress
    private val _showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val mutableList: MutableList<Book> = mutableListOf()
    public val liveData: LiveData<MutableList<Book>>
        get() = _liveData
    private val _liveData: MutableLiveData<MutableList<Book>> = MutableLiveData()

    companion object {
        private const val TAG = "QuanZiViewModel"
    }

    fun getBook(url: String) {
        launch({
            _showProgress.value = true
            val book = RetrofitClient.bookApiService.getBook(url)
            parse(book)
        })

    }

    private fun parse(html: String, callBack: CallBack? = null) {
        val soup = Jsoup.parse(html)
        val module = soup.select("div.module").select(".module-merge")
        val book_es = module[0].select("li.book-li")
        for (e in book_es) {
            val title = e.select("h4.book-title").text()
            val des = e.select("p.book-desc").text()
            val url = e.select("a.book-layout").attr("href")
            val coverUrl = e.select("img.book-cover")[0].attr("data-src")
            val author = e.select("span.book-author").text()
            mutableList.add(Book(url, title, des, coverUrl, author))
        }
        _liveData.postValue(mutableList)
        _showProgress.value = false


    }

    fun clear() {
        mutableList.clear()
        _liveData.postValue(mutableList)

    }

}