package com.jere.wanandroid_learning_kotlin.viewmodel.completeproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompleteProjectViewModel : ViewModel() {
    val projectTreeItemsLd: MutableLiveData<List<ProjectTreeItem>> = MutableLiveData()
    val projectItemListLd: MutableLiveData<ArticleList> = MutableLiveData()

    fun setProjectTreeItems() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                CompleteProjectRepository().getProjectTreeItem()
            }
            if (result is BaseResult.Success) {
                projectTreeItemsLd.value = result.data
            }
        }
    }

    fun setProjectItemList(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                CompleteProjectRepository().getCompleteProject(pageNumber, cid)
            }
            if (result is BaseResult.Success) {
                projectItemListLd.value = result.data
            }
        }
    }
}