package com.jere.wanandroid_learning_kotlin.ui.send

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TutorialViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tutorial Fragment"
    }
    val text: LiveData<String> = _text
}