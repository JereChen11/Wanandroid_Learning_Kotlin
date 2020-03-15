package com.jere.wanandroid_learning_kotlin.ui.knowledge_system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KnowledgeSystemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is knowledge system Fragment"
    }
    val text: LiveData<String> = _text
}