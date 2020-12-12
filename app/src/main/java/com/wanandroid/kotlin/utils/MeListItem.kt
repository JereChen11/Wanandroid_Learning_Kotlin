package com.wanandroid.kotlin.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.wanandroid.kotlin.R
import kotlinx.android.synthetic.main.custom_view_me_list_item.view.*

class MeListItem(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int
) :
    ConstraintLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    init {
        init(context, attributeSet)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_me_list_item, this, true)

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MeListItem)
        val titleTextString =
            typedArray.getString(R.styleable.MeListItem_meListItemTitleText)
        meListItemTitleTv.text = titleTextString

        val iconResourceId = typedArray.getResourceId(
            R.styleable.MeListItem_meListIconReference,
            R.drawable.default_profile
        )
        Glide.with(this).load(iconResourceId).into(meListItemIconIv)

        typedArray.recycle()
    }

    fun setTitleText(newTitle: String) {
        meListItemTitleTv.text = newTitle
        invalidate()
    }

}