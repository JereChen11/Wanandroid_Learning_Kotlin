package com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem

import com.jere.wanandroid_learning_kotlin.model.BaseRepository
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategory

class KnowledgeSystemRepository : BaseRepository() {

    suspend fun getKnowledgeSystemCategory(): BaseResult<List<KnowledgeSystemCategory>> {
        return safeApiCall("getKnowledgeSystemCategory", call = { requestKnowledgeSystemCategory() })
    }

    private suspend fun requestKnowledgeSystemCategory(): BaseResult<List<KnowledgeSystemCategory>> =
        executeResponse(ApiWrapper.getInstance().getKnowledgeSystemCategory())

    suspend fun getKnowledgeSystemArticleList(pageNumber: Int, id: Int): BaseResult<ArticleList> {
        return safeApiCall("getKnowledgeSystemArticleList", call = { requestKnowledgeSystemArticleList(pageNumber, id) })
    }

    private suspend fun requestKnowledgeSystemArticleList(pageNumber: Int, id: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getKnowledgeSystemArticleList(pageNumber, id))

}