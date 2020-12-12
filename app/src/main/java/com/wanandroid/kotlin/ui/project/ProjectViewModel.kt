package com.wanandroid.kotlin.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.model.bean.ArticleList
import com.wanandroid.kotlin.model.bean.ProjectTreeItemBean
import com.wanandroid.kotlin.model.repository.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectViewModel : ViewModel() {
    val projectTreeItemsLdBean: MutableLiveData<List<ProjectTreeItemBean>> = MutableLiveData()
    val projectItemListLd: MutableLiveData<ArticleList> = MutableLiveData()

    fun setProjectTreeItems() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                ProjectRepository()
                    .getProjectTreeItem()
            }
            if (result is BaseResult.Success) {
                projectTreeItemsLdBean.value = result.data
            }
        }
    }

    fun setProjectItemList(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                ProjectRepository()
                    .getCompleteProject(pageNumber, cid)
            }
            if (result is BaseResult.Success) {
                projectItemListLd.value = result.data
            }
        }
    }
}