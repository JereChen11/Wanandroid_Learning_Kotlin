package com.wanandroid.kotlin.ui.wechat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.WeChatBean
import com.wanandroid.kotlin.data.repository.WeChatRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeChatViewModel(private val repository: WeChatRepository) : ViewModel() {
    val weChatBeanLd: MutableLiveData<List<WeChatBean>> =
        MutableLiveData()
    val weChatArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun getWeChatBloggerListLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getWeChatBloggerList()
            }
            if (result is BaseResult.Success) {
                weChatBeanLd.value = result.data
            }
        }
    }

    fun setWeChatArticleListLd(authorId: Int, pageNumber: Int = 0) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getWeChatArticleList(authorId, pageNumber)
            }
            if (result is BaseResult.Success) {
                weChatArticleListLd.value = result.data
            }
        }
    }
}