package com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KnowledgeSystemViewModel : ViewModel() {
    val knowledgeSystemCategoryLd: MutableLiveData<List<KnowledgeSystemCategory>> =
        MutableLiveData()
    val knowledgeSystemArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setKnowledgeSystemCategoryLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                KnowledgeSystemRepository().getKnowledgeSystemCategory()
            }
            if (result is BaseResult.Success) {
                knowledgeSystemCategoryLd.value = result.data
            }
        }
    }

    fun setKnowledgeSystemArticleListLd(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                KnowledgeSystemRepository().getKnowledgeSystemArticleList(pageNumber, cid)
            }
            if (result is BaseResult.Success) {
                knowledgeSystemArticleListLd.value = result.data
            }
        }
    }
}