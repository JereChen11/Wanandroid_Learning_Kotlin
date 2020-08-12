package com.jere.wanandroid_learning_kotlin.model

data class BaseResponse<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)