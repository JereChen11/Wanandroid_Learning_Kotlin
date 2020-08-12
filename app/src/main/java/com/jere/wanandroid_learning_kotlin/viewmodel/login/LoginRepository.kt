package com.jere.wanandroid_learning_kotlin.viewmodel.login

import com.jere.wanandroid_learning_kotlin.model.BaseRepository
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.loginbeanfiles.LoginBean
import com.jere.wanandroid_learning_kotlin.utils.Settings

class LoginRepository : BaseRepository() {

    companion object {
        const val SUCCESSFUL = "successful"
        const val FAILED = "failed"
    }

    suspend fun login(paramsMap: Map<String, String>): BaseResult<LoginBean> {
        return safeApiCall("login", call = { requestLogin(paramsMap) })
    }

    private suspend fun requestLogin(paramsMap: Map<String, String>): BaseResult<LoginBean> =
        executeResponse(ApiWrapper.getInstance().login(paramsMap), {
            Settings.setIsLogin(true)
        }, {
            Settings.setIsLogin(false)
        })

    suspend fun register(paramsMap: Map<String, String>): BaseResult<LoginBean> {
        return safeApiCall("register", call = { requestRegister(paramsMap) })
    }

    private suspend fun requestRegister(paramsMap: Map<String, String>): BaseResult<LoginBean> =
        executeResponse(ApiWrapper.getInstance().register(paramsMap))
}