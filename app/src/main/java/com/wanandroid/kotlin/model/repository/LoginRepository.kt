package com.wanandroid.kotlin.model.repository

import com.wanandroid.kotlin.model.bean.UserBean
import com.wanandroid.kotlin.model.api.ApiWrapper
import com.wanandroid.kotlin.model.repository.base.BaseRepository
import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.utils.SpSettings

class LoginRepository : BaseRepository() {

    suspend fun login(paramsMap: Map<String, String>): BaseResult<UserBean> {
        return safeApiCall("login", call = { requestLogin(paramsMap) })
    }

    private suspend fun requestLogin(paramsMap: Map<String, String>): BaseResult<UserBean> =
        executeResponse(ApiWrapper.getInstance().login(paramsMap), {
            SpSettings.setIsLogin(true)
        }, {
            SpSettings.setIsLogin(false)
        })

    suspend fun register(paramsMap: Map<String, String>): BaseResult<UserBean> {
        return safeApiCall("register", call = { requestRegister(paramsMap) })
    }

    private suspend fun requestRegister(paramsMap: Map<String, String>): BaseResult<UserBean> =
        executeResponse(ApiWrapper.getInstance().register(paramsMap))
}