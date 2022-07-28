package com.blankspace.sakura.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blankspace.sakura.bean.Book
import com.blankspace.sakura.bean.Lesson

@Database(entities = [Lesson::class,Book::class],version = 1,exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun lessonDao():LessonDao
    abstract fun getBookDao():BookDao

    companion object{
        @Volatile private var instance: AppDataBase? = null
        private const val DATABASE_NAME = "reader_database"
        fun getInstance(context: Context): AppDataBase{
            return instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(context,AppDataBase::class.java, DATABASE_NAME)
                    .build().also { instance = it }
            }
        }
    }

}