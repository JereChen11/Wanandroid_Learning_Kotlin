package com.wanandroid.kotlin.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.HomeBannerBean
import com.wanandroid.kotlin.data.repository.HomeRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    val homeBannerBeanListLd: MutableLiveData<List<HomeBannerBean>> = MutableLiveData()
    val articleListLd: MutableLiveData<ArticleList> = MutableLiveData()

    fun setHomeBannerList() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getHomeBanner()
            }
            if (result is BaseResult.Success) {
                homeBannerBeanListLd.value = result.data
            } else if (result is BaseResult.Error) {
                //请求失败
                Log.e("jereTest", "setHomeBannerList: ${result.exception.message}")
            }
        }
    }

    fun setHomeArticleList(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getHomeArticle(pageNumber)
            }
            if (result is BaseResult.Success) {
                articleListLd.value = result.data
            }
        }
    }

}