package com.blankspace.sakura.net

class ApiException(var code: Int, override var message: String) : RuntimeException()