package com.blankspace.sakura.book.blibook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.book.blibook.utils.CallBack
import com.blankspace.sakura.book.blibook.utils.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BliViewModel : BaseViewModel() {
    val content: LiveData<String>
        get() = _content
    private var _content: MutableLiveData<String> = MutableLiveData()

    val showProgress: LiveData<Boolean>
        get() = _showProgress
    private val _showProgress: MutableLiveData<Boolean> = MutableLiveData()

    val loadImage: LiveData<Boolean>
        get() = _loadImage
    private var _loadImage: MutableLiveData<Boolean> = MutableLiveData()

    val urlComplete: LiveData<Boolean>
        get() = _urlComplete
    private var _urlComplete: MutableLiveData<Boolean> = MutableLiveData()


    private val bliRepository by lazy(LazyThreadSafetyMode.NONE) {
        BliRepository()
    }

    fun launchHomePage() {
        launch({
            _showProgress.value = true
            val homePage = bliRepository.getHomePage()
            CoroutineScope(Dispatchers.IO).launch {
                Parser.parse(homePage, object : CallBack {
                    override fun onSucceed(int: Int) {
                        when (int) {
                            0 -> {
                                _loadImage.postValue(true)
                                _showProgress.postValue(false)
                            }
                            1 -> {
                                _urlComplete.postValue(true)
                            }
                        }


                    }

                })
            }
            withContext(Dispatchers.Main) {
                _content.value = homePage
            }
        }, error = {

        })
    }
}