package com.wanandroid.kotlin.data.api.cookie

import com.wanandroid.kotlin.data.api.ApiWrapper
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

object PersistentCookieJar : CookieJar {
    private val store = PersistentCookieStore()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return store[url.host] ?: ArrayList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        store[url.host] = cookies
    }

    fun getCookieValue(host: String = ApiWrapper.BASE_HOST, name: String): String? {
        val cookies = store[host]
        cookies?.forEach {
            if (it.name == name) {
                return it.value
            }
        }
        return null
    }

    fun getCookie(host: String = ApiWrapper.BASE_HOST, name: String): String {
        val cookies = store[host]
        cookies?.forEach {
            if (it.name == name) {
                return it.toString()
            }
        }
        return ""
    }

    fun clearCookie(host: String = ApiWrapper.BASE_HOST) {
        store.remove(host)
    }
}