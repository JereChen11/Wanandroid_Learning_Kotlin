package com.jere.wanandroid_learning_kotlin.model.api

import android.content.Context
import com.jere.wanandroid_learning_kotlin.utils.MyApp
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = MyApp.instance.applicationContext
            .getSharedPreferences(ReceivedCookiesInterceptor.COOKIE_SP_KEY, Context.MODE_PRIVATE)
            .getStringSet(ReceivedCookiesInterceptor.COOKIE_KEY, null)
        if (cookies != null) {
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
        }

        return chain.proceed(builder.build())
    }


}