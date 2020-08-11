package com.jere.wanandroid_learning_kotlin.model.api

import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectItemList
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBannerListBean
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategoryBean
import com.jere.wanandroid_learning_kotlin.model.loginbeanfiles.LoginBean
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBloggerList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author jere
 */
interface ApiService {

    @GET("banner/json")
    suspend fun getHomeBannerList(): HomeBannerListBean

    @GET("article/list/{pageNumber}/json")
    suspend fun getHomeArticleList(@Path("pageNumber") pageNumber: Int): ArticleListBean


    @GET("project/tree/json")
    suspend fun getProjectTreeItems(): ProjectTreeItem

    @GET("project/list/{pageNumber}/json?")
    suspend fun getProjectItemList(
        @Path("pageNumber") pageNumber: Int,
        @Query("cid") cid: Int
    ): ProjectItemList

    @GET("wxarticle/chapters/json")
    suspend fun getWeChatBloggerList(): WeChatBloggerList

    @GET("/wxarticle/list/{authorId}/{pageNumber}/json")
    suspend fun getWeChatArticleList(
        @Path("authorId") authorId: Int,
        @Path("pageNumber") pageNumber: Int
    ): ArticleListBean

    @GET("tree/json")
    suspend fun getKnowledgeSystemCategory(): KnowledgeSystemCategoryBean

    @GET("article/list/0/json?")
    suspend fun getKnowledgeSystemArticleList(@Query("cid") cid: Int): ArticleListBean

    @POST("user/login")
    suspend fun login(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): LoginBean

    @POST("user/register")
    suspend fun register(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): LoginBean

    /**
     * 获取收藏的文章列表
     * @return
     */
    @GET("/lg/collect/list/{pageId}/json")
    suspend fun getCollectionArticleList(@Path("pageId") pageId: Int): ArticleListBean

    /**
     * 收藏文章
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Call<ResponseBody>

    /**
     * 取消文章收藏
     * @param id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Call<ResponseBody>
}