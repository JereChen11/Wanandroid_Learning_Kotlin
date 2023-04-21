package com.wanandroid.kotlin.ui.wechat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.data.repository.WeChatRepository

@Suppress("UNCHECKED_CAST")
class WeChatVmFactory(private val repository: WeChatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeChatViewModel(repository) as T
    }
}