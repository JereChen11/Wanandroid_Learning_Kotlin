package com.wanandroid.kotlin.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.ProjectTreeItemBean
import com.wanandroid.kotlin.data.repository.ProjectRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {
    val projectTreeItemsLdBean: MutableLiveData<List<ProjectTreeItemBean>> = MutableLiveData()
    val projectItemListLd: MutableLiveData<ArticleList> = MutableLiveData()

    fun setProjectTreeItems() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getProjectTreeItem()
            }
            if (result is BaseResult.Success) {
                projectTreeItemsLdBean.value = result.data
            }
        }
    }

    fun setProjectItemList(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.getCompleteProject(pageNumber, cid)
            }
            if (result is BaseResult.Success) {
                projectItemListLd.value = result.data
            }
        }
    }
}