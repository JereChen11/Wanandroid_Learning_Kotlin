package com.wanandroid.kotlin.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.databinding.CustomViewMeListItemBinding

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

    private lateinit var binding: CustomViewMeListItemBinding

    private fun init(context: Context?, attrs: AttributeSet?) {
        binding = CustomViewMeListItemBinding.inflate(LayoutInflater.from(context), this, true)

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MeListItem)
        val titleTextString =
            typedArray.getString(R.styleable.MeListItem_meListItemTitleText)
        binding.meListItemTitleTv.text = titleTextString

        val iconResourceId = typedArray.getResourceId(
            R.styleable.MeListItem_meListIconReference,
            R.drawable.default_profile
        )
        Glide.with(this).load(iconResourceId).into(binding.meListItemIconIv)

        typedArray.recycle()
    }

    fun setTitleText(newTitle: String) {
        binding.meListItemTitleTv.text = newTitle
        invalidate()
    }

}