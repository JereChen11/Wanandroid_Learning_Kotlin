package com.jere.wanandroid_learning_kotlin.viewmodel.mycollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCollectionViewModel : ViewModel() {

    val collectionArticleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setHomeArticleListLd(pageId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val articleListBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getCollectionArticleList(pageId)
            }
            collectionArticleListLd.value = articleListBean?.data?.datas
        }
    }
}