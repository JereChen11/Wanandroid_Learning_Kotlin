package com.wanandroid.kotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.data.bean.LoginResultBean
import com.wanandroid.kotlin.data.repository.LoginRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    val isLoginLdBean: MutableLiveData<LoginResultBean> = MutableLiveData()
    val isRegisterLdBean: MutableLiveData<LoginResultBean> = MutableLiveData()

    fun setIsLoginLd(username: String, password: String) {
        val paramsMap = mapOf("username" to username, "password" to password)
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.login(paramsMap)
            }
            if (result is BaseResult.Success) {
                isLoginLdBean.value =
                    LoginResultBean(
                        true,
                        result.data.username
                    )
            } else if (result is BaseResult.Error) {
                isLoginLdBean.value =
                    result.exception.message?.let {
                        LoginResultBean(
                            false,
                            it
                        )
                    }
            }
        }
    }

    fun setIsRegisterLd(username: String, password: String, repassword: String) {
        val paramsMap =
            mapOf("username" to username, "password" to password, "repassword" to repassword)
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.register(paramsMap)
            }
            if (result is BaseResult.Success) {
                isRegisterLdBean.value =
                    LoginResultBean(
                        true,
                        result.data.username
                    )
            } else if (result is BaseResult.Error) {
                isRegisterLdBean.value =
                    result.exception.message?.let {
                        LoginResultBean(
                            false,
                            it
                        )
                    }
            }
        }
    }
}