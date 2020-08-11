package com.jere.wanandroid_learning_kotlin.viewmodel.wechat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBloggerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeChatViewModel : ViewModel() {
    val weChatBloggerListLd: MutableLiveData<ArrayList<WeChatBloggerList.DataBean>> =
        MutableLiveData()
    val weChatArticleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setWeChatBloggerListLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val weChatBloggerList = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getWeChatBloggerList()
            }
            weChatBloggerListLd.value = weChatBloggerList?.data
        }
    }

    fun setWeChatArticleListLd(authorId: Int, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val articleListBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getWeChatArticleList(authorId, pageNumber)
            }
            weChatArticleListLd.value = articleListBean?.data?.datas
        }
    }
}