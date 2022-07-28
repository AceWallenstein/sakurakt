package com.blankspace.sakura.scan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankspace.sakura.data.BookRepository
import com.blankspace.sakura.scan.FileScanner

/**
 * created  by will on 2020/11/26 16:42
 */
class ScannerViewModelFactory(private val bookRepos: BookRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScannerViewModel(FileScanner(),bookRepos) as T
    }
}