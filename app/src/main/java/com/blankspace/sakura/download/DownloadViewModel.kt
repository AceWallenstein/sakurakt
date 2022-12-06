package com.blankspace.sakura.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankspace.sakura.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn

class DownloadViewModel : BaseViewModel() {
    private val respository: DownloadRespository by lazy(LazyThreadSafetyMode.NONE) {
        DownloadRespository()
    }
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
        get() = _name


    fun getData(username: String, password: String) {
        launch({
            respository.getData(username, password).flowOn(Dispatchers.IO).collectLatest { value ->
                _name.value = value
            }

        }, error = { exception ->
            _name.value = exception.message
        }, showErrorToast = false)
    }
}