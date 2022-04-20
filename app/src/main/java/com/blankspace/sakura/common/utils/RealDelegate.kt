package com.blankspace.sakura.common.utils

import kotlin.properties.Delegates

class RealDelegate : Subject by Delegate(){
    val apple by Delegates.observable("noname")
    { property, oldValue, newValue ->  println("$oldValue->$newValue")}
}