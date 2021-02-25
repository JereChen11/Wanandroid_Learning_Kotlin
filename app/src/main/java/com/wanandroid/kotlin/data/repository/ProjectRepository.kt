package com.wanandroid.kotlin.data.repository

import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.data.api.ApiWrapper
import com.wanandroid.kotlin.data.bean.ArticleList
import com.wanandroid.kotlin.data.bean.ProjectTreeItemBean
import com.wanandroid.kotlin.data.repository.base.BaseRepository

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
