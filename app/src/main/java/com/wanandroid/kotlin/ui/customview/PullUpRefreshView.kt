package com.wanandroid.kotlin.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.wanandroid.kotlin.databinding.CustomViewPullUpRefreshViewBinding

class PullUpRefreshView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int
) :
    ConstraintLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    init {
        init(context)
    }

    private lateinit var binding: CustomViewPullUpRefreshViewBinding

    private fun init(context: Context?) {
        binding = CustomViewPullUpRefreshViewBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    fun showIsLoadAllData() {
        binding.apply {
            loadingContainerLl.visibility = View.GONE
            noDataTv.visibility = View.VISIBLE
        }
    }

    fun showLoadingData() {
        binding.apply {
            loadingContainerLl.visibility = View.VISIBLE
            noDataTv.visibility = View.GONE
        }
    }

}