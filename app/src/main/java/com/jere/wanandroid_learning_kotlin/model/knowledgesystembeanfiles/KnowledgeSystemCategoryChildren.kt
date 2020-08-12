package com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles

data class KnowledgeSystemCategoryChildren(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)