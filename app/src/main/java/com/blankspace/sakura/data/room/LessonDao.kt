package com.blankspace.sakura.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.blankspace.sakura.bean.Lesson

@Dao
interface LessonDao {
    @Query("select * from lesson")
    fun getAll(): List<Lesson>

    @Insert
    fun insert(vararg lessons: Lesson)

    @Delete
    fun delete(lesson: Lesson)

    @Query("select * from lesson where id=:id")
    fun getLessonById(id: Int):Lesson
}