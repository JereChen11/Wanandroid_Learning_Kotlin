package com.wanandroid.kotlin.data.bean

/**
 * @author jere
 */
data class ProjectTreeItemBean(
        var courseId: Int,
        var id: Int,
        var name: String,
        var order: Int,
        var parentChapterId: Int,
        var isUserControlSetTop: Boolean,
        var visible: Int,
        var children: List<*>
)