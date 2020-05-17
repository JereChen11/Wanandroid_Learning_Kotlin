package com.jere.wanandroid_learning_kotlin.viewmodel.wechat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBloggerList

class WeChatViewModel : ViewModel() {
    companion object {
        const val TAG = "WeChatViewModel"
    }

    val weChatBloggerListLd: MutableLiveData<ArrayList<WeChatBloggerList.DataBean>> =
        MutableLiveData()
    val weChatArticleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setWeChatBloggerListLd() {
        ApiWrapper.getInstance()?.getWeChatBloggerList()
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val weChatArticleBloggerList: WeChatBloggerList =
                        gson.fromJson(responseBody, WeChatBloggerList::class.java)
                    weChatBloggerListLd.postValue(weChatArticleBloggerList.data)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, failedMsg)
                }

            })
    }

    fun setWeChatArticleListLd(authorId: Int, pageNumber: Int) {
        ApiWrapper.getInstance()?.getWeChatArticleList(authorId, pageNumber)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val weChatArticleList: ArticleListBean =
                        gson.fromJson(responseBody, ArticleListBean::class.java)
                    weChatArticleListLd.postValue(weChatArticleList.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, failedMsg)
                }

            })
    }
}