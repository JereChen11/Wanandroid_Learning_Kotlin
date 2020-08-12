package com.jere.wanandroid_learning_kotlin.viewmodel.mycollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCollectionViewModel : ViewModel() {

    val collectionArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setCollectionArticleListLd(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                MyCollectionRepository().getCollectionArticleList(pageNumber)
            }
            if (result is BaseResult.Success) {
                collectionArticleListLd.value = result.data
            }
        }
    }
}