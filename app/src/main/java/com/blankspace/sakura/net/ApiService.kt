package com.blankspace.sakura.net

import com.blankspace.sakura.bean.Course
import com.blankspace.sakura.bean.Lesson
import com.blankspace.sakura.bean.Pagination
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
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


}