package com.blankspace.sakura.calendar

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateFlowViewModel : ViewModel() {
    val stateFlow : LiveData<Int>
        get() = _stateFlow
    private val _stateFlow = MutableLiveData<Int>(0)

    fun add(v: View){
        _stateFlow.value=_stateFlow.value?.plus(1)

    }
    fun reduce(v: View){
        _stateFlow.value=_stateFlow.value?.minus(1)

    }
}