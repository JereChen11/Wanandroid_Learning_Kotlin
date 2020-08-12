package com.jere.wanandroid_learning_kotlin.viewmodel.home

import com.jere.wanandroid_learning_kotlin.model.BaseRepository
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBanner

class HomeRepository : BaseRepository() {

    suspend fun getHomeBanner(): BaseResult<List<HomeBanner>> {
        return safeApiCall("getHomeBanner", call = { requestBanners() })
    }

    private suspend fun requestBanners(): BaseResult<List<HomeBanner>> =
        executeResponse(ApiWrapper.getInstance().getHomeBannerList())

    suspend fun getHomeArticle(pageNumber: Int): BaseResult<ArticleList> {
        return safeApiCall("getHomeArticle", call = { requestArticle(pageNumber) })
    }

    private suspend fun requestArticle(pageNumber: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getHomeArticleList(pageNumber))

}