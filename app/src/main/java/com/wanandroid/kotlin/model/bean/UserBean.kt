package com.wanandroid.kotlin.model.bean

import com.google.gson.annotations.SerializedName

data class UserBean(
    @SerializedName("admin")
    var isAdmin: Boolean,
    var email: String,
    var icon: String,
    var id: Int,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String,
    var chapterTops: List<*>,
    var collectIds: List<Int>
)