package com.jere.wanandroid_learning_kotlin.viewmodel.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBannerListBean

class HomeViewModel : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    val homeBannerListLd: MutableLiveData<ArrayList<HomeBannerListBean.DataBean>> =
        MutableLiveData()
    val articleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setHomeBannerList() {
        ApiWrapper.getInstance()?.getHomeBannerList()
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val homeBannerListBean: HomeBannerListBean =
                        gson.fromJson(responseBody, HomeBannerListBean::class.java)
                    homeBannerListLd.postValue(homeBannerListBean.data)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, "failedMsg :$failedMsg")
                }

            })
    }

    fun setHomeArticleList(pageNumber: Int) {
        ApiWrapper.getInstance()?.getHomeArticleList(pageNumber)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val articleListBean: ArticleListBean =
                        gson.fromJson(responseBody, ArticleListBean::class.java)
                    articleListLd.postValue(articleListBean.data?.datas)
                }

                override fun getFailed(failedMsg: String) {

                }

            })
    }

}