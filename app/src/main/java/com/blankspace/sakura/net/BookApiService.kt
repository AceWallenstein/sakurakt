package com.blankspace.sakura.net

import com.blankspace.sakura.bean.Course
import com.blankspace.sakura.bean.Lesson
import com.blankspace.sakura.bean.Pagination
import retrofit2.http.*

interface BookApiService {

    companion object {
         var BASE_URL = "https://w.linovelib.com/"
    }

    @POST("user/login")
    suspend fun login(@QueryMap map: Map<String,@JvmSuppressWildcards Any>): BaseResult<String>

    @GET("article/list/{page}/json")
    suspend fun getArticle(@Path("page") page:Int):String
    @GET("chapter/547/sublist/json")
    suspend fun getCourse():BaseResult<List<Course>>
    //https://wanandroid.com/article/list/0/json?cid=549&order_type=1
    @GET("article/list/{page}/json")
    suspend fun getLesson(@Path("page")page:Int,@QueryMap map: Map<String, @JvmSuppressWildcards Any>):BaseResult<Pagination<Lesson>>

    @GET("/")
    suspend fun getHomePage():String

    @GET
    suspend fun parseCss(@Url url:String):String

    @GET
    suspend fun getBook(@Url url:String):String

    @GET
    suspend fun getBookIndex(@Url url:String):String



}