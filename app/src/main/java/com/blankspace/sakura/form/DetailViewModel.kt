package com.blankspace.sakura.form

import androidx.lifecycle.MutableLiveData
import com.blankspace.sakura.base.BaseViewModel

/**
 * Created by xiaojianjun on 2019-11-18.
 */
class DetailViewModel : BaseViewModel() {

    private val detailRepository by lazy { DetailRepository() }

    val collect = MutableLiveData<Boolean>()


    fun uncollect(id: Long) {
        launch(
            block = {

            },
            error = {
                collect.value = true
            }
        )
    }

}