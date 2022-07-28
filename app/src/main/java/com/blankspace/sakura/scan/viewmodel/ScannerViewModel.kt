package com.blankspace.sakura.scan.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankspace.sakura.bean.Book
import com.blankspace.sakura.data.BookRepository
import com.blankspace.sakura.ext.isBook
import com.blankspace.sakura.scan.FileItem
import com.blankspace.sakura.scan.FileScanner
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.io.File

/**
 * created  by will on 2020/11/22 17:34
 */
class ScannerViewModel(private val scanner: FileScanner, private val bookRepos: BookRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "LOG_TAG"
    }

    private val rootDir = Environment.getExternalStorageDirectory()

    private val list = MutableLiveData<MutableList<FileItem>>()
    private val currentCursor = MutableLiveData<String>()
    private val hasPermission = MutableLiveData<Boolean>()
    fun getList(): LiveData<MutableList<FileItem>> = list
    fun getCursor(): LiveData<String> = currentCursor
    fun hasPermission(): LiveData<Boolean> = hasPermission

    fun scan() {
        scanner.scan(
            viewModelScope,
            rootDir,
            { fileItem -> addFileItem(fileItem) },
            { cursor -> currentCursor.postValue(cursor) })
    }

    fun setHasPermission(which: Boolean) {
        hasPermission.value = which
    }

    fun copyUriContentToAppStorage(uri: Uri, context: Context) {
        val input = context.contentResolver.openInputStream(uri)
        val dest = context.openFileOutput("", Context.MODE_PRIVATE)
    }

    private fun addFileItem(item: FileItem) {
        viewModelScope.launch(Main) {
            //addFileItem是个异步回调，因为操作的是同一个变量，所以这里同步处理
            synchronized(list) {
                val value = list.value ?: mutableListOf()
                value.add(item)
                list.value = value
            }
        }
    }

    fun changeSelectState(index: Int) {
        val value = list.value!!
        val oldItem = value[index]
        value[index] = oldItem.copy(selected = !oldItem.selected)
        list.value = value
    }

    fun save(file: File) {
        if (!file.exists()) {
            Log.w(TAG, "file: ${file.path} does not exits,save canceled")
            return
        }
        if (!file.isBook()) {
            Log.w(TAG, "file: ${file.path} is not book,save canceled")
            return
        }
        viewModelScope.launch {
            bookRepos.saveBook(Book.build(file))
        }
    }

    private fun save(fileItems: List<FileItem>) {
        val list = mutableListOf<Book>()
        fileItems.forEach {
            val file = File(it.path)
            if (file.exists() && file.isBook()) {
                list.add(Book.build(file))
            } else {
                Log.w(TAG, "ignore file: ${file.path},cause it does not exist or is not book")
            }
        }
        viewModelScope.launch {
            bookRepos.saveBook(list)
        }
    }

    fun saveSelectedFile() {
        val selected = list.value?.filter { it.selected }
        selected?.let {
            save(it)
        }
    }
}