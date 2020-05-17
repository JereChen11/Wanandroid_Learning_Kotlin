package com.jere.wanandroid_learning_kotlin.viewmodel.mycollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean

class MyCollectionViewModel : ViewModel() {

    val collectionArticleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setHomeArticleListLd(pageId: Int) {
        ApiWrapper.getInstance()?.getCollectionArticleList(pageId)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val articleListBean: ArticleListBean =
                        Gson().fromJson(responseBody, ArticleListBean::class.java)
                    val datas: ArrayList<ArticleListBean.DataBean.DatasBean> =
                        articleListBean.data?.datas!!
                    for (data in datas) {
                        data.isCollect = true
                    }
                    collectionArticleListLd.postValue(articleListBean.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                }

            })
    }
}