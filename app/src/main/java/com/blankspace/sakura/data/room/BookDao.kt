package com.blankspace.sakura.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.blankspace.sakura.bean.Book

/**
 * created  by will on 2020/11/22 10:50
 */
@Dao
interface BookDao {

    @Query("SELECT * FROM book ORDER BY lastReadTime DESC")
    fun getAllBookInPage(): PagingSource<Int, Book>

    @Query("SELECT * FROM book WHERE id= :id")
    fun getBookById(id: Long): Book

    @Query("SELECT * FROM book WHERE path= :path" )
    fun getBookByPath(path: String): Book?

    @Insert
    fun saveBook(book: Book)

    @Insert
    fun saveBook(bookList: List<Book>)

    @Update
    fun updateBook(book: Book)

    @Delete
    fun deleteBook(book: Book)
}