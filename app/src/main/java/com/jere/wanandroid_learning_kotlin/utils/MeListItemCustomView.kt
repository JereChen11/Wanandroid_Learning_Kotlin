package com.jere.wanandroid_learning_kotlin.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.jere.wanandroid_learning_kotlin.R
import kotlinx.android.synthetic.main.custom_view_me_list_item.view.*

class MeListItemCustomView(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    init {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_me_list_item, this, true)

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MeListItemCustomView)
        val titleTextString =
            typedArray.getString(R.styleable.MeListItemCustomView_meListItemTitleText)
        meListItemTitleTv.text = titleTextString

        val iconResourceId = typedArray.getResourceId(
            R.styleable.MeListItemCustomView_meListIconReference,
            R.drawable.head_portrait
        )
        Glide.with(this).load(iconResourceId).into(meListItemIconIv)

        typedArray.recycle()
    }

    fun setTitleText(newTitle: String) {
        meListItemTitleTv.text = newTitle
        invalidate()
    }

}