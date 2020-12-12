package com.wanandroid.kotlin.model.repository.base

data class BaseResponse<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)