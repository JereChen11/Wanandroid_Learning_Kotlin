package com.jere.wanandroid_learning_kotlin.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jere.wanandroid_learning_kotlin.R

/**
 * @author jere
 */
class HomeBottomBarItemCustomView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {
    private fun init(
        context: Context,
        attrs: AttributeSet
    ) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_home_bottom_bar, this, true)
        val iconIv =
            findViewById<ImageView>(R.id.home_bottom_bar_item_icon_iv)
        val contentTv = findViewById<TextView>(R.id.home_bottom_bar_item_content_tv)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.HomeBottomBarItemCustomView)
        val iconResourceId = typedArray.getResourceId(
            R.styleable.HomeBottomBarItemCustomView_iconReference,
            R.drawable.ic_launcher_background
        )
        iconIv.setImageResource(iconResourceId)
        val contentString =
            typedArray.getString(R.styleable.HomeBottomBarItemCustomView_contentTv)
        if (contentString != null) {
            contentTv.text = contentString
        }
        typedArray.recycle()
    }

    init {
        init(context, attrs)
    }
}