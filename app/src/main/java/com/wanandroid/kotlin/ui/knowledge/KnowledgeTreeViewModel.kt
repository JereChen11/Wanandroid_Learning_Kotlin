package com.wanandroid.kotlin.ui.knowledge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.model.bean.ArticleList
import com.wanandroid.kotlin.model.bean.KnowledgeTree
import com.wanandroid.kotlin.model.repository.KnowledgeTreeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KnowledgeTreeViewModel : ViewModel() {
    val knowledgeTreeLd: MutableLiveData<List<KnowledgeTree>> =
        MutableLiveData()
    val knowledgeSystemArticleListLd: MutableLiveData<ArticleList> =
        MutableLiveData()

    fun setKnowledgeSystemCategoryLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                KnowledgeTreeRepository()
                    .getKnowledgeSystemCategory()
            }
            if (result is BaseResult.Success) {
                knowledgeTreeLd.value = result.data
            }
        }
    }

    fun setKnowledgeSystemArticleListLd(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                KnowledgeTreeRepository()
                    .getKnowledgeSystemArticleList(pageNumber, cid)
            }
            if (result is BaseResult.Success) {
                knowledgeSystemArticleListLd.value = result.data
            }
        }
    }
}