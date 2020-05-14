package com.jere.wanandroid_learning_kotlin.model.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author jere
 */
interface ApiService {

    @GET("banner/json")
    fun getHomeBannerList(): Call<ResponseBody>

    @GET("article/list/{pageNumber}/json")
    fun getHomeArticleList(@Path("pageNumber") pageNumber: Int): Call<ResponseBody>


    @GET("project/tree/json")
    fun getProjectTreeItems(): Call<ResponseBody>

    @GET("project/list/{pageNumber}/json?")
    fun getProjectItemList(
        @Path("pageNumber") pageNumber: Int,
        @Query("cid") cid: Int
    ): Call<ResponseBody>

    @GET("wxarticle/chapters/json")
    fun getWeChatBloggerList(): Call<ResponseBody>

    @GET("/wxarticle/list/{authorId}/{pageNumber}/json")
    fun getWeChatArticleList(
        @Path("authorId") authorId: Int,
        @Path("pageNumber") pageNumber: Int
    ): Call<ResponseBody>

    @GET("tree/json")
    fun getKnowledgeSystemCategory(): Call<ResponseBody>

    @GET("article/list/0/json?")
    fun getKnowledgeSystemArticleList(@Query("cid") cid: Int): Call<ResponseBody>

    @POST("user/login")
    fun login(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): Call<ResponseBody>

    @POST("user/register")
    fun register(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): Call<ResponseBody>

    /**
     * 获取收藏的文章列表
     * @return
     */
    @GET("/lg/collect/list/{pageId}/json")
    fun getCollectionArticleList(@Path("pageId") pageId: Int): Call<ResponseBody>

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