package com.jere.wanandroid_learning_kotlin.model.articlebeanfile

import com.google.gson.annotations.SerializedName
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article

data class ArticleList(
    var curPage: Int,
    @SerializedName("datas")
    var articles: List<Article>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)