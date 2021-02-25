package com.wanandroid.kotlin.data.repository

import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.data.api.ApiWrapper
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.WeChatBean
import com.wanandroid.kotlin.data.repository.base.BaseRepository

class WeChatRepository : BaseRepository() {

    suspend fun getWeChatBloggerList(): BaseResult<List<WeChatBean>> {
        return safeApiCall("getWeChatBloggerList", call = { requestWeChatBloggerList() })
    }

    private suspend fun requestWeChatBloggerList(): BaseResult<List<WeChatBean>> =
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