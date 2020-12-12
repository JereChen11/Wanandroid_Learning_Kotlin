package com.wanandroid.kotlin.model.bean

import com.google.gson.annotations.SerializedName

data class KnowledgeTree(
    @SerializedName("children")
    var children: List<KnowledgeTreeChildren>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)

data class KnowledgeTreeChildren(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)