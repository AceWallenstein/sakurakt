package com.blankspace.sakura.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankspace.sakura.App
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.net.MoshiHelper
import com.blankspace.sakura.net.RetrofitClient
import com.blankspace.sakura.utils.toast
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Field

class LoginViewModel : BaseViewModel() {
    private var _login_state = MutableLiveData<Boolean>()
    val login_state
        get() = _login_state

    fun login(username: String, password: String) {
        val map = mapOf("username" to username, "password" to password)
        launch({
            val login = RetrofitClient.apiService.login(map).apiData()
        })
    }
}