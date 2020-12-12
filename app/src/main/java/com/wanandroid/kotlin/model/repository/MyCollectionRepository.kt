package com.wanandroid.kotlin.model.repository

import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.model.api.ApiWrapper
import com.wanandroid.kotlin.model.bean.ArticleList
import com.wanandroid.kotlin.model.repository.base.BaseRepository

class MyCollectionRepository : BaseRepository() {

    suspend fun getCollectionArticleList(pageNumber: Int): BaseResult<ArticleList> {
        return safeApiCall(
            "getCollectionArticleList",
            call = { requestCollectionArticleList(pageNumber) })
    }

    private suspend fun requestCollectionArticleList(pageNumber: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getCollectionArticleList(pageNumber))
}