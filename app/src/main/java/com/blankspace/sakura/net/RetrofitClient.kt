package com.blankspace.sakura.net

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {



    /**log**/
    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**OkhttpClient*/
    private fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(logInterceptor)
        }
        return builder.build()
    }

    /**Retrofit*/
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient())
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.moshi))
        .build()

    /**ApiService*/
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}