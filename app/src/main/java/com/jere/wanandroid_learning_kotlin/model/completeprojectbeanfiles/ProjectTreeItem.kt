package com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles

/**
 * @author jere
 */
data class ProjectTreeItem(
        var courseId: Int,
        var id: Int,
        var name: String,
        var order: Int,
        var parentChapterId: Int,
        var isUserControlSetTop: Boolean,
        var visible: Int,
        var children: List<*>
)