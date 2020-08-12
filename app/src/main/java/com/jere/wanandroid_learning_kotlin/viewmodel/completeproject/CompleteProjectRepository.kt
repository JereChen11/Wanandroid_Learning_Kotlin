package com.jere.wanandroid_learning_kotlin.viewmodel.completeproject

import com.jere.wanandroid_learning_kotlin.model.BaseRepository
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem

class CompleteProjectRepository : BaseRepository() {

    suspend fun getProjectTreeItem(): BaseResult<List<ProjectTreeItem>> {
        return safeApiCall("getProjectTreeItem", call = { requestProjectTreeItem() })
    }

    private suspend fun requestProjectTreeItem(): BaseResult<List<ProjectTreeItem>> =
        executeResponse(ApiWrapper.getInstance().getProjectTreeItems())

    suspend fun getCompleteProject(pageNumber: Int, id: Int): BaseResult<ArticleList> {
        return safeApiCall("getCompleteProject", call = { requestCompleteProject(pageNumber, id) })
    }

    private suspend fun requestCompleteProject(
        pageNumber: Int,
        id: Int
    ): BaseResult<ArticleList> =
        executeResponse(ApiWrapper.getInstance().getProjectItemList(pageNumber, id))

}
