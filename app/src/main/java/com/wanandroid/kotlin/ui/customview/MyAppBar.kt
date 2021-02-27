package com.wanandroid.kotlin.ui.customview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.databinding.CustomViewMyAppBarBinding

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

    private lateinit var binding: CustomViewMyAppBarBinding

    private fun init(context: Context?, attrs: AttributeSet?) {
        binding = CustomViewMyAppBarBinding.inflate(LayoutInflater.from(context), this, true)

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MyAppBar)
        val titleString = typedArray.getString(R.styleable.MyAppBar_myAppBarTitleText)
        binding.myAppBarTitleTv.apply {
            text = titleString
            setOnClickListener { v: View -> (v.context as Activity).onBackPressed() }
        }

        typedArray.recycle()
    }

    fun setTitle(newTitle: String) {
        binding.myAppBarTitleTv.text = newTitle
        invalidate()
    }
}