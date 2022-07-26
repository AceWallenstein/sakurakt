package com.blankspace.sakura.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blankspace.sakura.bean.Lesson

@Database(entities = [Lesson::class],version = 1,exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun lessonDao():LessonDao
}