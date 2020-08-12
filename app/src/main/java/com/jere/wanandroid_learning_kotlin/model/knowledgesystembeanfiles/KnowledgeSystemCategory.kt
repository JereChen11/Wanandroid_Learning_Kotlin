package com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles

import com.google.gson.annotations.SerializedName

data class KnowledgeSystemCategory(
    @SerializedName("children")
    var children: List<KnowledgeSystemCategoryChildren>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)