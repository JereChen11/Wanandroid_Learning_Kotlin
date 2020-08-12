package com.jere.wanandroid_learning_kotlin.viewmodel.mycollection

import com.jere.wanandroid_learning_kotlin.model.BaseRepository
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList

class MyCollectionRepository : BaseRepository() {

    suspend fun getCollectionArticleList(pageNumber: Int): BaseResult<ArticleList> {
        return safeApiCall(
            "getCollectionArticleList",
            call = { requestCollectionArticleList(pageNumber) })
    }

    private suspend fun requestCollectionArticleList(pageNumber: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getCollectionArticleList(pageNumber))
}