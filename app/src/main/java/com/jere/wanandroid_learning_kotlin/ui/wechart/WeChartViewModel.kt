package com.jere.wanandroid_learning_kotlin.ui.wechart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeChartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is weChart Fragment"
    }
    val text: LiveData<String> = _text
}