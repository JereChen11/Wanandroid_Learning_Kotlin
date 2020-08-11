package com.jere.wanandroid_learning_kotlin.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBannerListBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    val homeBannerListLd: MutableLiveData<ArrayList<HomeBannerListBean.DataBean>> =
        MutableLiveData()
    val articleListLd: MutableLiveData<ArrayList<ArticleListBean.DataBean.DatasBean>> =
        MutableLiveData()

    fun setHomeBannerList() {
        viewModelScope.launch(Dispatchers.Main) {
            val homeBannerListBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getHomeBannerList()
            }
            homeBannerListLd.value = homeBannerListBean?.data
        }
    }

    fun setHomeArticleList(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val articleListBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getHomeArticleList(pageNumber)
            }
            articleListLd.value = articleListBean?.data?.datas
        }
    }

}