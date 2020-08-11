package com.jere.wanandroid_learning_kotlin.viewmodel.completeproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectItemList
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompleteProjectViewModel : ViewModel() {
    val projectTreeItemsLd: MutableLiveData<ArrayList<ProjectTreeItem.DataBean>> = MutableLiveData()
    val projectItemListLd: MutableLiveData<ArrayList<ProjectItemList.DataBean.DatasBean>> = MutableLiveData()

    fun setProjectTreeItems() {
        viewModelScope.launch(Dispatchers.Main) {
            val projectTreeItem = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getProjectTreeItems()
            }
            projectTreeItemsLd.value = projectTreeItem?.data
        }
    }

    fun setProjectItemList(pageNumber: Int, cid: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val projectItemList = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.getProjectItemList(pageNumber, cid)
            }
            projectItemListLd.value = projectItemList?.data?.datas
        }
    }
}