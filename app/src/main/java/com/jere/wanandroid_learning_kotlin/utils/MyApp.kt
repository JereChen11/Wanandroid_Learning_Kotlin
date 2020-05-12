package com.jere.wanandroid_learning_kotlin.utils

import android.app.Application

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}