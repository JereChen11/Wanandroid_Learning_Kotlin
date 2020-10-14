package com.jere.wanandroid_learning_kotlin.model

import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper

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