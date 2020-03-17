package com.jere.wanandroid_learning_kotlin.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author jere
 */
interface ApiService {

    @GET("banner/json")
    fun getHomeBannerList() : Call<ResponseBody>


    @GET("project/tree/json")
    fun getProjectTreeItems(): Call<ResponseBody>

    @GET("project/list/{pageNumber}/json?")
    fun getProjectItemList(
        @Path("pageNumber") pageNumber: Int,
        @Query("cid") cid: Int
    ): Call<ResponseBody>


}