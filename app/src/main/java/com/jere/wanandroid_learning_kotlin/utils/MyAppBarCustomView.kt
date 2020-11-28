package com.jere.wanandroid_learning_kotlin.utils

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.jere.wanandroid_learning_kotlin.R
import kotlinx.android.synthetic.main.my_app_bar_custom_view.view.*

class MyAppBarCustomView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.my_app_bar_custom_view, this, true)

        myAppBarBackIv.setOnClickListener { v: View -> (v.context as Activity).onBackPressed() }

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MyAppBarCustomView)
        val titleString = typedArray.getString(R.styleable.MyAppBarCustomView_myAppBarTitleText)
        myAppBarTitleTv.text = titleString

        typedArray.recycle()
    }

    fun setTitle(newTitle: String) {
        myAppBarTitleTv.text = newTitle
        invalidate()
    }
}