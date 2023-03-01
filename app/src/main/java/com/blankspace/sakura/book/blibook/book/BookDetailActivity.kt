package com.blankspace.sakura.book.blibook.book

import androidx.activity.viewModels
import com.blankspace.sakura.base.BaseActivity
import com.blankspace.sakura.book.blibook.model.Book
import com.blankspace.sakura.databinding.ActivityBookDetailBinding

class BookDetailActivity : BaseActivity<ActivityBookDetailBinding>() {
    companion object {
        const val PARAM_ARTICLE = "book"
        const val BASE_URL = "https://w.linovelib.com"
    }

    private val viewModel by viewModels<BookDetailViewModel>()
    private lateinit var book: Book


    override fun getViewBinding(): ActivityBookDetailBinding =
        ActivityBookDetailBinding.inflate(layoutInflater)

    override fun initData() {
        book = intent?.getParcelableExtra(PARAM_ARTICLE) ?: return
        val href = book.url.substring(0, book.url.lastIndexOf(".")) + "/catalog"
        val pageUrl =  href
        viewModel.parse(pageUrl)
    }

}