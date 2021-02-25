package com.wanandroid.kotlin.data.api

import com.wanandroid.kotlin.data.api.cookie.PersistentCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiWrapper {


    const val BASE_HOST: String = "www.wanandroid.com"
    private const val BASE_URL: String = "https://www.wanandroid.com/"

    fun getInstance(): ApiService {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .cookieJar(PersistentCookieJar)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }


}