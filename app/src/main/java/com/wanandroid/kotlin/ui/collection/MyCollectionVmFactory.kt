package com.wanandroid.kotlin.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.data.repository.MyCollectionRepository

@Suppress("UNCHECKED_CAST")
class MyCollectionVmFactory(private val repository: MyCollectionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyCollectionViewModel(repository) as T
    }
}