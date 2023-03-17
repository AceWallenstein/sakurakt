package com.blankspace.sakura.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginVideoModelFactory(val name: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(name) as T
    }
}