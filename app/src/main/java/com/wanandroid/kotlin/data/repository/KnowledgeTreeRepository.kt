package com.wanandroid.kotlin.data.repository

import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.KnowledgeTree
import com.wanandroid.kotlin.data.api.ApiWrapper
import com.wanandroid.kotlin.data.repository.base.BaseRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult

class KnowledgeTreeRepository : BaseRepository() {

    suspend fun getKnowledgeSystemCategory(): BaseResult<List<KnowledgeTree>> {
        return safeApiCall("getKnowledgeSystemCategory", call = { requestKnowledgeSystemCategory() })
    }

    private suspend fun requestKnowledgeSystemCategory(): BaseResult<List<KnowledgeTree>> =
        executeResponse(ApiWrapper.getInstance().getKnowledgeTree())

    suspend fun getKnowledgeSystemArticleList(pageNumber: Int, id: Int): BaseResult<ArticleList> {
        return safeApiCall("getKnowledgeSystemArticleList", call = { requestKnowledgeSystemArticleList(pageNumber, id) })
    }

    private suspend fun requestKnowledgeSystemArticleList(pageNumber: Int, id: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getKnowledgeTreeArticleList(pageNumber, id))

}