package com.blankspace.sakura.data.room

import androidx.room.Room
import com.blankspace.sakura.App
import com.blankspace.sakura.bean.Lesson

/**
 * Created by xiaojianjun on 2019-12-05.
 */
object RoomHelper {

    private val appDatabase by lazy {
        Room.databaseBuilder(App.instance, AppDataBase::class.java, "database_sakura").build()
    }

    private val lessonDao by lazy { appDatabase.lessonDao() }

    suspend fun queryAllLessons():List<Lesson>{
        return lessonDao.getAll()
    }
    suspend fun deleteLesson(lesson: Lesson){
        lessonDao.delete(lesson)
    }

//    suspend fun queryAllReadHistory(): List<Lesson> {
//        return lessonDao.getAll().map {
//            it.article.apply { tags = it.tags }
//        }
//    }
//
//    suspend fun addReadHistory(article: Article) {
//        article.readTime = System.currentTimeMillis()
//        lessonDao.insertArticle(article)
//        article.tags.forEach {
//            lessonDao.insertTag(it.apply {
//                it.articleId = article.id
//            })
//        }
//    }
//
//    suspend fun deleteReadHistory(article: Article) {
//        lessonDao.queryReadHistory(article.id)?.let { readHistory ->
//            lessonDao.deleteArticle(readHistory.article)
//            readHistory.tags.forEach { lessonDao.deleteTag(it) }
//        }
//    }
}