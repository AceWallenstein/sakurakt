package com.blankspace.sakura.book.blibook.test

import com.blankspace.sakura.base.BaseViewModel
import com.blankspace.sakura.book.blibook.di.MobilePhone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(mobilePhone: MobilePhone) : BaseViewModel() {
}