package com.wanandroid.kotlin.data.repository

import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.data.api.ApiWrapper
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.repository.base.BaseRepository

class MyCollectionRepository : BaseRepository() {

    suspend fun getCollectionArticleList(pageNumber: Int): BaseResult<ArticleList> {
        return safeApiCall(
            "getCollectionArticleList",
            call = { requestCollectionArticleList(pageNumber) })
    }

    private suspend fun requestCollectionArticleList(pageNumber: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getCollectionArticleList(pageNumber))
}