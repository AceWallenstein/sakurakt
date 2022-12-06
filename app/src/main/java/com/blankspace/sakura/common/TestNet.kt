package com.blankspace.sakura.common

class TestNet {

    companion object {

        private const val TAG = "TestNet"
    }

    var callback:SuccessCallback? = null
    fun setOnSuccessCallback(callback:SuccessCallback){
        this.callback = callback
    }
    fun setOnSuccessCallbackDsl(init: SuccessCallbackImpl.() -> Unit){
        val listener = SuccessCallbackImpl()
        init(listener)
        this.setOnSuccessCallback(listener)
    }


    class SuccessCallbackImpl:SuccessCallback{
        private var onSuccess: ((String) -> String)? = null

        private var doSth: (() -> Unit)? = null

        fun onSuccess(method: (String) -> String) {
            onSuccess = method
        }

        fun doSth(method: () -> Unit) {
            doSth = method
        }

        override fun onSuccess(str: String): String {
            return onSuccess?.invoke(str).toString()
        }

        override fun doSth() {
            doSth?.invoke()
        }

    }


    interface SuccessCallback {
        fun onSuccess(str:String):String
        fun  doSth()
    }


    interface FailedCallback {
        fun onFailed(str:String)
        fun doSth()
    }
}