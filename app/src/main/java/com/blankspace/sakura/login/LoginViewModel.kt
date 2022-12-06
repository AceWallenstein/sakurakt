package com.blankspace.sakura.login

import androidx.lifecycle.MutableLiveData
import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.net.RetrofitClient

class LoginViewModel : BaseViewModel() {
    private var _login_state = MutableLiveData<Boolean>()
    val login_state
        get() = _login_state

    fun login(username: String, password: String) {
        val map = mapOf("username" to username, "password" to password)
        launch({
            val login = RetrofitClient.apiService.login(map).apiData()
            _login_state.value = true
        }, error = {
            _login_state.value = true
        })
    }

}