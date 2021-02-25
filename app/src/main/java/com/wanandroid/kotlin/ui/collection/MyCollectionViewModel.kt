package com.wanandroid.kotlin.ui.collection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.repository.MyCollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCollectionViewModel : ViewModel() {

    val collectionArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setCollectionArticleListLd(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                MyCollectionRepository()
                    .getCollectionArticleList(pageNumber)
            }
            if (result is BaseResult.Success) {
                collectionArticleListLd.value = result.data
            }
        }
    }
}