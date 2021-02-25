package com.wanandroid.kotlin.ui.knowledge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.data.repository.KnowledgeTreeRepository

class KnowledgeTreeVmFactory(private val repository: KnowledgeTreeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KnowledgeTreeViewModel(repository) as T
    }

}