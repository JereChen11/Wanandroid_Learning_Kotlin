package com.wanandroid.kotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.kotlin.model.repository.base.BaseResult
import com.wanandroid.kotlin.model.bean.LoginResultBean
import com.wanandroid.kotlin.model.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    val isLoginLdBean: MutableLiveData<LoginResultBean> = MutableLiveData()
    val isRegisterLdBean: MutableLiveData<LoginResultBean> = MutableLiveData()

    fun setIsLoginLd(username: String, password: String) {
        val paramsMap = mapOf("username" to username, "password" to password)
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                LoginRepository()
                    .login(paramsMap)
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
                LoginRepository()
                    .register(paramsMap)
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