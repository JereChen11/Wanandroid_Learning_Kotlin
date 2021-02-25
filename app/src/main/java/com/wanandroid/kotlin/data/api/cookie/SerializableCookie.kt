package com.wanandroid.kotlin.data.api.cookie

import okhttp3.Cookie
import java.io.Serializable

class SerializableCookie(cookie: Cookie) : Serializable {
    private val name: String = cookie.name
    private val value: String? = cookie.value
    private val expiresAt: Long? = cookie.expiresAt
    private val domain: String = cookie.domain
    private val path: String? = cookie.path
    private val secure: Boolean? = cookie.secure
    private val httpOnly: Boolean? = cookie.httpOnly
    private val hostOnly: Boolean? = cookie.hostOnly

    fun cookie(): Cookie {
        return Cookie.Builder()
            .name(name)
            .value(value ?: "")
            .expiresAt(expiresAt ?: 0L)
            .path(path ?: "/")
            .let {
                if (secure == true) it.secure()
                if (httpOnly == true) it.httpOnly()
                if (hostOnly == true) {
                    it.hostOnlyDomain(domain)
                } else {
                    it.domain(domain)
                }
                it
            }.build()
    }
}