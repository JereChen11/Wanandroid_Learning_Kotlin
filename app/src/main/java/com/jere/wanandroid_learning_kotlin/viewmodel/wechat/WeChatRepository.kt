package com.jere.wanandroid_learning_kotlin.viewmodel.wechat

import com.jere.wanandroid_learning_kotlin.model.BaseRepository
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBlogger

class WeChatRepository : BaseRepository() {

    suspend fun getWeChatBloggerList(): BaseResult<List<WeChatBlogger>> {
        return safeApiCall("getWeChatBloggerList", call = { requestWeChatBloggerList() })
    }

    private suspend fun requestWeChatBloggerList(): BaseResult<List<WeChatBlogger>> =
        executeResponse(ApiWrapper.getInstance().getWeChatBloggerList())

    suspend fun getWeChatArticleList(authorId: Int, pageNumber: Int): BaseResult<ArticleList> {
        return safeApiCall(
            "getWeChatArticleList",
            call = { requestWeChatArticleList(authorId, pageNumber) })
    }

    private suspend fun requestWeChatArticleList(
        authorId: Int,
        pageNumber: Int
    ): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getWeChatArticleList(authorId, pageNumber))

}