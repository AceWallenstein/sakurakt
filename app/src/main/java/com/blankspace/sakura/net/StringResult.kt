package com.blankspace.sakura.net

import androidx.annotation.Keep

@Keep
class StringResult(
    val errorCode: Int,
    val errorMsg: String,
    @DataString
    val  data: String?
)