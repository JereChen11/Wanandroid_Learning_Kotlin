package com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles

data class WeChatBlogger(
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var isUserControlSetTop: Boolean,
    var visible: Int,
    var children: List<*>
)