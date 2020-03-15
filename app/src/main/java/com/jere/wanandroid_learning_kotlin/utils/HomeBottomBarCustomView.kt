package com.jere.wanandroid_learning_kotlin.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.jere.wanandroid_learning_kotlin.R

class HomeBottomBarCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    fun init(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.custom_view_home_bottom_bar, this)

    }

}
