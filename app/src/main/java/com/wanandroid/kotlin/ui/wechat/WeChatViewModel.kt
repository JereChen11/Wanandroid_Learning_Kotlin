package com.wanandroid.kotlin.ui.wechat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.model.bean.ArticleList
import com.wanandroid.kotlin.model.bean.WeChatBean
import com.wanandroid.kotlin.model.repository.WeChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeChatViewModel : ViewModel() {
    val weChatBeanLd: MutableLiveData<List<WeChatBean>> =
        MutableLiveData()
    val weChatArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setWeChatBloggerListLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                WeChatRepository()
                    .getWeChatBloggerList()
            }
            if (result is BaseResult.Success) {
                weChatBeanLd.value = result.data
            }
        }
    }

    fun setWeChatArticleListLd(authorId: Int, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                WeChatRepository()
                    .getWeChatArticleList(authorId, pageNumber)
            }
            if (result is BaseResult.Success) {
                weChatArticleListLd.value = result.data
            }
        }
    }
}