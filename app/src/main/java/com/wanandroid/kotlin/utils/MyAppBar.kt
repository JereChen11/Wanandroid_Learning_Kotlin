package com.wanandroid.kotlin.utils

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.wanandroid.kotlin.R
import kotlinx.android.synthetic.main.custom_view_my_app_bar.view.*

class MyAppBar(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int
) : LinearLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    init {
        init(context, attributeSet)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_my_app_bar, this, true)

        myAppBarBackIv.setOnClickListener { v: View -> (v.context as Activity).onBackPressed() }

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MyAppBar)
        val titleString = typedArray.getString(R.styleable.MyAppBar_myAppBarTitleText)
        myAppBarTitleTv.text = titleString

        typedArray.recycle()
    }

    fun setTitle(newTitle: String) {
        myAppBarTitleTv.text = newTitle
        invalidate()
    }
}