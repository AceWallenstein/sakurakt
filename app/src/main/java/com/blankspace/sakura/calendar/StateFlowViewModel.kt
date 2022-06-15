package com.blankspace.sakura.calendar

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class StateFlowViewModel : ViewModel() {
    val stateFlow = MutableStateFlow<Int>(0)

    fun add(v: View){
        stateFlow.value++

    }
    fun reduce(v: View){
        stateFlow.value--

    }
}