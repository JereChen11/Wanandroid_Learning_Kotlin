package com.wanandroid.kotlin.model.bean

data class HomeBannerBean(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)