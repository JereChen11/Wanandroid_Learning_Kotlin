package com.wanandroid.kotlin.model.api

import com.wanandroid.kotlin.utils.SpSettings
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiWrapper {

    private lateinit var instance: ApiService

    //const 用于修饰常量
    private const val BASE_URL: String = "https://www.wanandroid.com/"

    fun getInstance(): ApiService {
        val okHttpClient: OkHttpClient = if (!SpSettings.getIsLogin()) {
            OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(ReceivedCookiesInterceptor())
                .build()
        } else {
            OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(AddCookiesInterceptor())
                .build()
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        instance = retrofit.create(ApiService::class.java)
        return instance
    }
}