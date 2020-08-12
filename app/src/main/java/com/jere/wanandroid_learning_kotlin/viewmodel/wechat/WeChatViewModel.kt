package com.jere.wanandroid_learning_kotlin.viewmodel.wechat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBlogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeChatViewModel : ViewModel() {
    val weChatBloggerLd: MutableLiveData<List<WeChatBlogger>> =
        MutableLiveData()
    val weChatArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setWeChatBloggerListLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                WeChatRepository().getWeChatBloggerList()
            }
            if (result is BaseResult.Success) {
                weChatBloggerLd.value = result.data
            }
        }
    }

    fun setWeChatArticleListLd(authorId: Int, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                WeChatRepository().getWeChatArticleList(authorId, pageNumber)
            }
            if (result is BaseResult.Success) {
                weChatArticleListLd.value = result.data
            }
        }
    }
}