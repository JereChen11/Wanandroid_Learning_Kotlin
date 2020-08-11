package com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategoryBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KnowledgeSystemViewModel : ViewModel() {
    val knowledgeSystemCategoryLd: MutableLiveData<ArrayList<KnowledgeSystemCategoryBean.DataBean>> =
        MutableLiveData()
    val knowledgeSystemArticleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setKnowledgeSystemCategoryLd() {
        viewModelScope.launch(Dispatchers.Main) {
            val knowledgeSystemCategoryBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getKnowledgeSystemCategory()
            }
            knowledgeSystemCategoryLd.value = knowledgeSystemCategoryBean?.data
        }
    }

    fun setKnowledgeSystemArticleListLd(cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val articleListBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getKnowledgeSystemArticleList(cid)
            }
            knowledgeSystemArticleListLd.value = articleListBean?.data?.datas
        }
    }
}