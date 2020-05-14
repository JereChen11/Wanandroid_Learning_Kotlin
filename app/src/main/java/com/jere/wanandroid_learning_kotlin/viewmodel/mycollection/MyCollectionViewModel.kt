package com.jere.wanandroid_learning_kotlin.viewmodel.mycollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.CollectionRepository
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeArticleListBean

class MyCollectionViewModel : ViewModel() {

    val collectionArticleListLd: MutableLiveData<HomeArticleListBean> = MutableLiveData()

    fun setHomeArticleListLd(pageId: Int) {
        fun getMyCollectionArticleList(
            pageId: Int,
            listener: CollectionRepository.CollectOrUnCollectListener
        ) {
            ApiWrapper.getInstance()?.getCollectionArticleList(pageId)
                ?.enqueue(object : AbstractRetrofitCallback() {
                    override fun getSuccessful(responseBody: String) {
                        var homeArticleListBean: HomeArticleListBean =
                            Gson().fromJson(responseBody, HomeArticleListBean::class.java)
                        collectionArticleListLd.postValue(homeArticleListBean)
                    }

                    override fun getFailed(failedMsg: String) {
                    }

                })
        }
    }
}