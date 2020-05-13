package com.jere.wanandroid_learning_kotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.loginbeanfiles.LoginBean
import com.jere.wanandroid_learning_kotlin.utils.Settings

class LoginViewModel : ViewModel() {

    val isLoginLd: MutableLiveData<Boolean> = MutableLiveData()
    val isRegisterLd: MutableLiveData<Boolean> = MutableLiveData()

    fun setIsLoginLd(username: String, password: String) {
        val paramsMap = mapOf("username" to username, "password" to password)

        ApiWrapper.getInstance()?.login(paramsMap)?.enqueue(object : AbstractRetrofitCallback() {
            override fun getSuccessful(responseBody: String) {
                val gson: Gson = Gson()
                val loginBean: LoginBean = gson.fromJson(responseBody, LoginBean::class.java)
                if (loginBean.getErrorCode() == 0) {
                    isLoginLd.postValue(true)
                    Settings.setIsLogin(true)
                }
            }

            override fun getFailed(failedMsg: String) {
                isLoginLd.postValue(false)
            }

        })
    }

    fun setIsRegisterLd(username: String, password: String, repassword: String) {
        val paramsMap = mapOf("username" to username, "password" to password, "repassword" to repassword)

        ApiWrapper.getInstance()?.register(paramsMap)?.enqueue(object : AbstractRetrofitCallback() {
            override fun getSuccessful(responseBody: String) {

            }

            override fun getFailed(failedMsg: String) {
            }

        })
    }
}