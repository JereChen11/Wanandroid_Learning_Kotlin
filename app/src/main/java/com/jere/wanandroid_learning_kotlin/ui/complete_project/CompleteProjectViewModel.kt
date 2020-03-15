package com.jere.wanandroid_learning_kotlin.ui.complete_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompleteProjectViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is complete project Fragment"
    }
    val text: LiveData<String> = _text
}