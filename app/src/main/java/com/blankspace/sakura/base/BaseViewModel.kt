package com.blankspace.sakura.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankspace.sakura.App
import com.blankspace.sakura.R
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.net.ApiException
import com.blankspace.sakura.net.MoshiHelper
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit


open class BaseViewModel : ViewModel() {
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    protected fun createBody(map: Map<String, String>): RequestBody {
        val string = MoshiHelper.toJson(map)
        return string.toRequestBody("application/json;charset=utf-8".toMediaType())

    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效，清除用户信息、清除cookie/token
//                        UserInfoStore.clearUserInfo()
//                        loginStatusInvalid.value = true
                    }
                    // 其他api错误
                    -1 -> if (showErrorToast) App.instance.toast(e.message)
                    // 其他错误
                    else -> if (showErrorToast) App.instance.toast(e.message)
                }
            }
            // 网络请求失败
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                if (showErrorToast) App.instance.toast(R.string.network_request_failed)
            // 数据解析错误
            is JsonDataException, is JsonEncodingException ->
                if (showErrorToast) App.instance.toast(R.string.api_data_parse_error)
            // 其他错误
            else ->
                if (showErrorToast) App.instance.toast(e.message ?: return)
        }
    }

}