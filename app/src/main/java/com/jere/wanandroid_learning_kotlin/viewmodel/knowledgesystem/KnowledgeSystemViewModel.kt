package com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeArticleListBean
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemArticleListBean
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategoryBean

class KnowledgeSystemViewModel : ViewModel() {

    companion object {
        const val TAG = "KnowledgeSystemVm"
    }

    val knowledgeSystemCategoryLd: MutableLiveData<ArrayList<KnowledgeSystemCategoryBean.DataBean>> =
        MutableLiveData()
    val knowledgeSystemArticleListLd: MutableLiveData<ArrayList<HomeArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setKnowledgeSystemCategoryLd() {
        ApiWrapper.getInstance()?.getKnowledgeSystemCategory()
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val knowledgeSystemCategoryBean: KnowledgeSystemCategoryBean =
                        gson.fromJson(responseBody, KnowledgeSystemCategoryBean::class.java)
                    knowledgeSystemCategoryLd.postValue(knowledgeSystemCategoryBean.data)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, failedMsg)
                }

            })
    }

    fun setKnowledgeSystemArticleListLd(cid: Int) {
        ApiWrapper.getInstance()?.getKnowledgeSystemArticleList(cid)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val homeArticleListBean: HomeArticleListBean =
                        gson.fromJson(responseBody, HomeArticleListBean::class.java)
                    knowledgeSystemArticleListLd.postValue(homeArticleListBean.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, failedMsg)
                }

            })
    }
}