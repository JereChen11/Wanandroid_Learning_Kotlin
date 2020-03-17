package com.jere.wanandroid_learning_kotlin.ui.complete_project

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.ApiService
import com.jere.wanandroid_learning_kotlin.model.ApiWrapper2
import com.jere.wanandroid_learning_kotlin.model.completeproject.ProjectItemList
import com.jere.wanandroid_learning_kotlin.model.completeproject.ProjectTreeItem

class CompleteProjectViewModel : ViewModel() {

    companion object {
        const val TAG: String = "CompleteProjectVm"
    }

    val projectTreeItemsLd: MutableLiveData<ArrayList<ProjectTreeItem.DataBean>> = MutableLiveData()
    val projectItemListLd: MutableLiveData<ArrayList<ProjectItemList.DataBean.DatasBean>> = MutableLiveData()

    fun setProjectTreeItems() {
        val apiService: ApiService? = ApiWrapper2.getInstance()
        apiService?.getProjectTreeItems()?.enqueue(object : AbstractRetrofitCallback() {
            override fun getSuccessful(responseBody: String) {
                val gson = Gson()
                val projectTreeItem: ProjectTreeItem =
                    gson.fromJson(responseBody, ProjectTreeItem::class.java)
                projectTreeItemsLd.postValue(projectTreeItem.data)
            }

            override fun getFailed(failedMsg: String) {
                Log.e(TAG, failedMsg)
            }
        })

    }

    fun setProjectItemList(pageNumber: Int, cid: Int) {
        val apiService: ApiService? = ApiWrapper2.getInstance()
        apiService?.getProjectItemList(pageNumber, cid)
            ?.enqueue(object : AbstractRetrofitCallback() {
                override fun getSuccessful(responseBody: String) {
                    val gson = Gson()
                    val projectItemList: ProjectItemList =
                        gson.fromJson(responseBody, ProjectItemList::class.java)
                    projectItemListLd.postValue(projectItemList.data?.datas)
                }

                override fun getFailed(failedMsg: String) {
                }
            })
    }
}