package com.wanandroid.kotlin.ui.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.data.repository.ProjectRepository

@Suppress("UNCHECKED_CAST")
class ProjectVmFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProjectViewModel(repository) as T
    }
}