package com.jere.wanandroid_learning_kotlin.model.loginbeanfiles

data class LoginBean(
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