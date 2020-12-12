package com.wanandroid.kotlin.model.repository

import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.model.api.ApiWrapper
import com.wanandroid.kotlin.model.bean.ArticleList
import com.wanandroid.kotlin.model.bean.ProjectTreeItemBean
import com.wanandroid.kotlin.model.repository.base.BaseRepository

class ProjectRepository : BaseRepository() {

    suspend fun getProjectTreeItem(): BaseResult<List<ProjectTreeItemBean>> {
        return safeApiCall("getProjectTreeItem", call = { requestProjectTreeItem() })
    }

    private suspend fun requestProjectTreeItem(): BaseResult<List<ProjectTreeItemBean>> =
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
