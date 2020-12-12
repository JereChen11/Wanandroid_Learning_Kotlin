package com.wanandroid.kotlin.model.api

import android.content.Context
import com.wanandroid.kotlin.MyApp
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class ReceivedCookiesInterceptor : Interceptor {
    companion object {
        const val COOKIE_SP_KEY: String = "COOKIE_SP"
        const val COOKIE_KEY: String = "cookie"
        private const val SET_COOKIE_KEY = "Set-Cookie"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers(SET_COOKIE_KEY).isNotEmpty()) {
            val cookies = HashSet(originalResponse.headers(SET_COOKIE_KEY))
            val sp = MyApp.instance.applicationContext.getSharedPreferences(
                COOKIE_SP_KEY,
                Context.MODE_PRIVATE
            )
            sp.edit().putStringSet(COOKIE_KEY, cookies).apply()
        }
        return originalResponse
    }

}