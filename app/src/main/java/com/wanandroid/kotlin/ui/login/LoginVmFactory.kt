package com.wanandroid.kotlin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.data.repository.LoginRepository

@Suppress("UNCHECKED_CAST")
class LoginVmFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}