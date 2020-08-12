package com.jere.wanandroid_learning_kotlin.model.api

import com.jere.wanandroid_learning_kotlin.model.BaseResponse
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBanner
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategory
import com.jere.wanandroid_learning_kotlin.model.loginbeanfiles.LoginBean
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBlogger
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author jere
 */
interface ApiService {

    @GET("banner/json")
    suspend fun getHomeBannerList(): BaseResponse<List<HomeBanner>>

    @GET("article/list/{pageNumber}/json")
    suspend fun getHomeArticleList(@Path("pageNumber") pageNumber: Int): BaseResponse<ArticleList>


    @GET("project/tree/json")
    suspend fun getProjectTreeItems(): BaseResponse<List<ProjectTreeItem>>

    @GET("project/list/{pageNumber}/json?")
    suspend fun getProjectItemList(
        @Path("pageNumber") pageNumber: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ArticleList>

    @GET("wxarticle/chapters/json")
    suspend fun getWeChatBloggerList(): BaseResponse<List<WeChatBlogger>>

    @GET("/wxarticle/list/{authorId}/{pageNumber}/json")
    suspend fun getWeChatArticleList(
        @Path("authorId") authorId: Int,
        @Path("pageNumber") pageNumber: Int
    ): BaseResponse<ArticleList>

    @GET("tree/json")
    suspend fun getKnowledgeSystemCategory(): BaseResponse<List<KnowledgeSystemCategory>>

    @GET("article/list/0/json?")
    suspend fun getKnowledgeSystemArticleList(@Query("cid") cid: Int): BaseResponse<ArticleList>

    @POST("user/login")
    suspend fun login(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): BaseResponse<LoginBean>

    @POST("user/register")
    suspend fun register(@QueryMap paramsMap: @JvmSuppressWildcards Map<String, Any>): BaseResponse<LoginBean>

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
    fun collectArticle(@Path("id") id: Int): Call<ResponseBody>

    /**
     * 取消文章收藏
     * @param id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Call<ResponseBody>
}