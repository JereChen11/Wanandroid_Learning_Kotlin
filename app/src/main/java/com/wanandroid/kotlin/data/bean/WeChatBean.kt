package com.wanandroid.kotlin.data.bean

data class WeChatBean(
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var isUserControlSetTop: Boolean,
    var visible: Int,
    var children: List<*>
)