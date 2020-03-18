package com.jere.wanandroid_learning_kotlin.ui.wechat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.ApiWrapper2
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBloggerList
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatArticleList

class WeChatViewModel : ViewModel() {
    companion object {
        const val TAG = "WeChatViewModel"
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is weChart Fragment"
    }
    val text: LiveData<String> = _text

    val weChatBloggerListLd: MutableLiveData<ArrayList<WeChatBloggerList.DataBean>> =
        MutableLiveData()
    val weChatArticleListLd: MutableLiveData<ArrayList<WeChatArticleList.DataBean.DatasBean>> =
        MutableLiveData()

    fun setWeChatBloggerListLd() {
        ApiWrapper2.getInstance()?.getWeChatBloggerList()
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
        ApiWrapper2.getInstance()?.getWeChatArticleList(authorId, pageNumber)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val weChatArticleList: WeChatArticleList =
                        gson.fromJson(responseBody, WeChatArticleList::class.java)
                    weChatArticleListLd.postValue(weChatArticleList.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                    Log.e(TAG, failedMsg)
                }

            })
    }
}