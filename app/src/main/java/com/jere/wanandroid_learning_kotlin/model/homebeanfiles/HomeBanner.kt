package com.jere.wanandroid_learning_kotlin.model.homebeanfiles

data class HomeBanner(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)