package com.wanandroid.kotlin.ui.knowledge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.KnowledgeTree
import com.wanandroid.kotlin.data.repository.KnowledgeTreeRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KnowledgeTreeViewModel(private val repository: KnowledgeTreeRepository) : ViewModel() {
    val knowledgeTreeLd: MutableLiveData<List<KnowledgeTree>> =
        MutableLiveData()
    val knowledgeSystemArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setKnowledgeSystemCategoryLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getKnowledgeSystemCategory()
            }
            if (result is BaseResult.Success) {
                knowledgeTreeLd.value = result.data
            }
        }
    }

    fun setKnowledgeSystemArticleListLd(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getKnowledgeSystemArticleList(pageNumber, cid)
            }
            if (result is BaseResult.Success) {
                knowledgeSystemArticleListLd.value = result.data
            }
        }
    }
}