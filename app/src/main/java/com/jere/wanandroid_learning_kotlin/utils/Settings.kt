package com.jere.wanandroid_learning_kotlin.utils

import android.content.Context
import android.content.SharedPreferences

object Settings {

    private const val SETTINGS_SP_KEY: String = "SETTINGS_SP"
    private const val IS_LOGIN_KEY = "IS_LOGIN"

    private fun getSp() : SharedPreferences {
        return MyApp.instance.applicationContext.getSharedPreferences(SETTINGS_SP_KEY, Context.MODE_PRIVATE)
    }

    fun getIsLogin(): Boolean {
        return getSp().getBoolean(IS_LOGIN_KEY, false)
    }

    fun setIsLogin(isLogin: Boolean) {
        getSp().edit().putBoolean(IS_LOGIN_KEY, isLogin).apply()
    }

}