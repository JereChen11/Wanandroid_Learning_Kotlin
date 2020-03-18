package com.jere.wanandroid_learning_kotlin.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.ApiWrapper2
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeArticleListBean
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBannerListBean

class HomeViewModel : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    val homeBannerListLd: MutableLiveData<ArrayList<HomeBannerListBean.DataBean>> =
        MutableLiveData()
    val homeArticleListLd: MutableLiveData<ArrayList<HomeArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setHomeBannerList() {
        ApiWrapper2.getInstance()?.getHomeBannerList()
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
        ApiWrapper2.getInstance()?.getHomeArticleList(pageNumber)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val homeArticleListBean: HomeArticleListBean =
                        gson.fromJson(responseBody, HomeArticleListBean::class.java)
                    homeArticleListLd.postValue(homeArticleListBean.data?.datas)
                }

                override fun getFailed(failedMsg: String) {

                }

            })
    }

}