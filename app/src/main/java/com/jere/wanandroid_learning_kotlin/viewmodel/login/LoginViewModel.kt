package com.jere.wanandroid_learning_kotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.loginbeanfiles.LoginOrRegisterSuccessBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    val isLoginLd: MutableLiveData<LoginOrRegisterSuccessBean> = MutableLiveData()
    val isRegisterLd: MutableLiveData<LoginOrRegisterSuccessBean> = MutableLiveData()

    fun setIsLoginLd(username: String, password: String) {
        val paramsMap = mapOf("username" to username, "password" to password)
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                LoginRepository().login(paramsMap)
            }
            if (result is BaseResult.Success) {
                isLoginLd.value = LoginOrRegisterSuccessBean(true, result.data.username)
            } else if (result is BaseResult.Error) {
                isLoginLd.value =
                    result.exception.message?.let { LoginOrRegisterSuccessBean(false, it) }
            }
        }
    }

    fun setIsRegisterLd(username: String, password: String, repassword: String) {
        val paramsMap =
            mapOf("username" to username, "password" to password, "repassword" to repassword)
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                LoginRepository().register(paramsMap)
            }
            if (result is BaseResult.Success) {
                isRegisterLd.value = LoginOrRegisterSuccessBean(true, result.data.username)
            } else if (result is BaseResult.Error) {
                isRegisterLd.value =
                    result.exception.message?.let { LoginOrRegisterSuccessBean(false, it) }
            }
        }
    }
}