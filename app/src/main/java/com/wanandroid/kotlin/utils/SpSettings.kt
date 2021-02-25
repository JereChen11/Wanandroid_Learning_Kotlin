package com.wanandroid.kotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.wanandroid.kotlin.MyApp

object SpSettings {

    private const val SETTINGS_SP_KEY: String = "SETTINGS_SP"
    private const val IS_LOGIN_KEY = "IS_LOGIN"
    private const val USERNAME_KEY = "USERNAME"
    private const val AVATAR_URI_STRING_KEY = "AVATAR_URI_STRING"

    private fun getSp() : SharedPreferences {
        return MyApp.context.getSharedPreferences(SETTINGS_SP_KEY, Context.MODE_PRIVATE)
    }

    fun getIsLogin(): Boolean {
        return getSp().getBoolean(IS_LOGIN_KEY, false)
    }

    fun setIsLogin(isLogin: Boolean) {
        getSp().edit().putBoolean(IS_LOGIN_KEY, isLogin).apply()
    }

    fun getUsername(): String? {
        return getSp().getString(USERNAME_KEY, "username")
    }

    fun setUsername(username: String) {
        getSp().edit().putString(USERNAME_KEY, username).apply()
    }

    fun getAvatarUriString(): String? {
        return getSp().getString(AVATAR_URI_STRING_KEY, "")
    }

    fun setAvatarUriString(uriString: String) {
        getSp().edit().putString(AVATAR_URI_STRING_KEY, uriString).apply()
    }

}