package com.jere.wanandroid_learning_kotlin.viewmodel.mycollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeArticleListBean

class MyCollectionViewModel : ViewModel() {

    val collectionArticleListLd: MutableLiveData<ArrayList<HomeArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setHomeArticleListLd(pageId: Int) {
        ApiWrapper.getInstance()?.getCollectionArticleList(pageId)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val homeArticleListBean: HomeArticleListBean =
                        Gson().fromJson(responseBody, HomeArticleListBean::class.java)
                    val datas: ArrayList<HomeArticleListBean.DataBean.DatasBean> =
                        homeArticleListBean.data?.datas!!
                    for (data in datas) {
                        data.isCollect = true
                    }
                    collectionArticleListLd.postValue(homeArticleListBean.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                }

            })
    }
}