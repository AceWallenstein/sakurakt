package com.blankspace.sakura.common

class Domain {

    companion object {
        private const val TAG = "Domain"
    }

    fun main() {
//        TestNet().requestNet(onSuccess = {
//            this.onSuccess()
//        }, onFail = {
//
//            it.onFailed()
//        })

       TestNet().setOnSuccessCallbackDsl {
           onSuccess { "" }
       }

    }
}