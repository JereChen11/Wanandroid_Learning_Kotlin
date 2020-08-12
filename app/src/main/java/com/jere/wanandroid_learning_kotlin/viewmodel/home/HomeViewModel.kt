package com.jere.wanandroid_learning_kotlin.viewmodel.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    val homeBannerListLd: MutableLiveData<List<HomeBanner>> = MutableLiveData()
    val articleListLd: MutableLiveData<ArticleList> = MutableLiveData()

    fun setHomeBannerList() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                HomeRepository().getHomeBanner()
            }
            if (result is BaseResult.Success) {
                homeBannerListLd.value = result.data
            } else if (result is BaseResult.Error) {
                //请求失败
                Log.e("jereTest", "setHomeBannerList: ${result.exception.message}")
            }
        }
    }

    fun setHomeArticleList(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                HomeRepository().getHomeArticle(pageNumber)
            }
            if (result is BaseResult.Success) {
                articleListLd.value = result.data
            }
        }
    }

}