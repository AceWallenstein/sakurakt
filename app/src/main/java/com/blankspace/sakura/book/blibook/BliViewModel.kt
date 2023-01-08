package com.blankspace.sakura.book.blibook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankspace.sakura.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BliViewModel : BaseViewModel() {

    public val content: LiveData<String>
        get() = _content
    private var _content: MutableLiveData<String> = MutableLiveData()

    private val bliRepository by lazy(LazyThreadSafetyMode.NONE) {
        BliRepository()
    }

    fun launchHomePage() {
        launch({
            val homePage = bliRepository.getHomePage()
            withContext(Dispatchers.Main) {
                _content.value = homePage
            }
        }, error = {

        })
    }
}