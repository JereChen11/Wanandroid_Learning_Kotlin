package com.wanandroid.kotlin.data.repository

import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.data.api.ApiWrapper
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.HomeBannerBean
import com.wanandroid.kotlin.data.repository.base.BaseRepository

class HomeRepository : BaseRepository() {

    suspend fun getHomeBanner(): BaseResult<List<HomeBannerBean>> {
        return safeApiCall("getHomeBanner", call = { requestBanners() })
    }

    private suspend fun requestBanners(): BaseResult<List<HomeBannerBean>> =
        executeResponse(ApiWrapper.getInstance().getHomeBannerList())

    suspend fun getHomeArticle(pageNumber: Int): BaseResult<ArticleList> {
        return safeApiCall("getHomeArticle", call = { requestArticle(pageNumber) })
    }

    private suspend fun requestArticle(pageNumber: Int): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getHomeArticleList(pageNumber))

}