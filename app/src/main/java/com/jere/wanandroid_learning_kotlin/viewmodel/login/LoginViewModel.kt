package com.jere.wanandroid_learning_kotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper
import com.jere.wanandroid_learning_kotlin.model.loginbeanfiles.LoginBean
import com.jere.wanandroid_learning_kotlin.utils.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    val isLoginLd: MutableLiveData<LoginBean> = MutableLiveData()
    val isRegisterLd: MutableLiveData<LoginBean> = MutableLiveData()

    fun setIsLoginLd(username: String, password: String) {
        val paramsMap = mapOf("username" to username, "password" to password)
        viewModelScope.launch(Dispatchers.Main) {
            val loginBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.login(paramsMap)
            }
            isLoginLd.value = loginBean
//            if (loginBean?.errorCode == 0) {
//                isLoginLd.value = true
//                Settings.setIsLogin(true)
//            }
        }
    }

    fun setIsRegisterLd(username: String, password: String, repassword: String) {
        val paramsMap =
            mapOf("username" to username, "password" to password, "repassword" to repassword)
        viewModelScope.launch(Dispatchers.Main) {
            val registerLoginBean = withContext(Dispatchers.IO) {
                ApiWrapper.getInstance()?.register(paramsMap)
            }
            isRegisterLd.value = registerLoginBean
//            if (loginBean?.errorCode == 0) {
//                isRegisterLd.value = true
//                Settings.setIsLogin(true)
//            } else {
//                isRegisterLd.value = false
//            }
        }
//        ApiWrapper.getInstance()?.register(paramsMap)?.enqueue(object : AbstractRetrofitCallback() {
//            override fun getSuccessful(responseBody: String) {
//                isRegisterLd.value = true
//            }
//
//            override fun getFailed(failedMsg: String) {
//                isRegisterLd.value = false
//            }
//
//        })
    }
}