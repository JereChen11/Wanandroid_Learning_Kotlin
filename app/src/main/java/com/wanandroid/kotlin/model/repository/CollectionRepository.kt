package com.wanandroid.kotlin.model.repository

import com.wanandroid.kotlin.model.api.ApiWrapper
import com.wanandroid.kotlin.model.repository.base.BaseRepository
import com.wanandroid.kotlin.model.repository.base.BaseResult

class CollectionRepository : BaseRepository() {

    suspend fun collectArticle(articleId: Int): BaseResult<Any> {
        return safeApiCall("collectArticle", call = { requestCollectArticle(articleId) })
    }

    private suspend fun requestCollectArticle(articleId: Int): BaseResult<Any> =
        executeResponse(ApiWrapper.getInstance().collectArticle(articleId))

    suspend fun unCollectArticle(articleId: Int): BaseResult<Any> {
        return safeApiCall("unCollectArticle", call = { requestUnCollectArticle(articleId) })
    }

    private suspend fun requestUnCollectArticle(articleId: Int): BaseResult<Any> =
        executeResponse(ApiWrapper.getInstance().unCollectArticle(articleId))

}