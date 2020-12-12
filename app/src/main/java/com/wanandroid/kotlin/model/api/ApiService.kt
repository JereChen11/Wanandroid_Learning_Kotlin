package com.wanandroid.kotlin.model.api

import com.wanandroid.kotlin.model.repository.base.BaseResponse
import com.wanandroid.kotlin.model.bean.ArticleList
import com.wanandroid.kotlin.model.bean.ProjectTreeItemBean
import com.wanandroid.kotlin.model.bean.HomeBannerBean
import com.wanandroid.kotlin.model.bean.KnowledgeTree
import com.wanandroid.kotlin.model.bean.UserBean
import com.wanandroid.kotlin.model.bean.WeChatBean
import retrofit2.http.*

/**
 * @author jere
 */
interface ApiService {

    /**
     * 获取主页Banner数据
     */
    @GET("banner/json")
    suspend fun getHomeBannerList(): BaseResponse<List<HomeBannerBean>>

    /**
     * 获取主页文章列表
     */
    @GET("article/list/{pageNumber}/json")
    suspend fun getHomeArticleList(@Path("pageNumber") pageNumber: Int): BaseResponse<ArticleList>

    /**
     * 获取项目分类
     */
    @GET("project/tree/json")
    suspend fun getProjectTreeItems(): BaseResponse<List<ProjectTreeItemBean>>

    /**
     * 获取项目文章列表
     */
    @GET("project/list/{pageNumber}/json?")
    suspend fun getProjectItemList(
        @Path("pageNumber") pageNumber: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ArticleList>

    /**
     * 获取微信公众号博主
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWeChatBloggerList(): BaseResponse<List<WeChatBean>>

    /**
     * 获取微信公众号文章
     */
    @GET("/wxarticle/list/{authorId}/{pageNumber}/json")
    suspend fun getWeChatArticleList(
        @Path("authorId") authorId: Int,
        @Path("pageNumber") pageNumber: Int
    ): BaseResponse<ArticleList>

    /**
     * 获取知识体系类别
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): BaseResponse<List<KnowledgeTree>>

    /**
     * 获取知识体系文章列表
     */
    @GET("article/list/{pageNumber}/json?")
    suspend fun getKnowledgeTreeArticleList(
        @Path("pageNumber") pageNumber: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ArticleList>

    /**
     * 登入
     */
    @POST("user/login")
    suspend fun login(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): BaseResponse<UserBean>

    /**
     * 注册
     */
    @POST("user/register")
    suspend fun register(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): BaseResponse<UserBean>

    /**
     * 获取收藏的文章列表
     * @return
     */
    @GET("/lg/collect/list/{pageId}/json")
    suspend fun getCollectionArticleList(@Path("pageId") pageId: Int): BaseResponse<ArticleList>

    /**
     * 收藏文章
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BaseResponse<Any>

    /**
     * 取消文章收藏
     * @param id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BaseResponse<Any>
}