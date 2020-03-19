package com.jere.wanandroid_learning_kotlin.ui.knowledge_system

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.ApiWrapper2
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemArticleListBean
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategoryBean

class KnowledgeSystemViewModel : ViewModel() {

    companion object {
        const val TAG = "KnowledgeSystemVm"
    }

    val knowledgeSystemCategoryLd: MutableLiveData<ArrayList<KnowledgeSystemCategoryBean.DataBean>> =
        MutableLiveData()
    val knowledgeSystemArticleListLd: MutableLiveData<ArrayList<KnowledgeSystemArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setKnowledgeSystemCategoryLd() {
        ApiWrapper2.getInstance()?.getKnowledgeSystemCategory()
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
        ApiWrapper2.getInstance()?.getKnowledgeSystemArticleList(cid)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val knowledgeSystemArticleListBean: KnowledgeSystemArticleListBean =
                        gson.fromJson(responseBody, KnowledgeSystemArticleListBean::class.java)
                    knowledgeSystemArticleListLd.postValue(knowledgeSystemArticleListBean.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, failedMsg)
                }

            })
    }
}