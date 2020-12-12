package com.wanandroid.kotlin.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.wanandroid.kotlin.R
import kotlinx.android.synthetic.main.custom_view_pull_up_refresh_view.view.*

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

    private fun init(context: Context?) {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.custom_view_pull_up_refresh_view, this, true)

    }

    fun showIsLoadAllData() {
        loadingContainerLl.visibility = View.GONE
        noDataTv.visibility = View.VISIBLE
    }

    fun showLoadingData() {
        loadingContainerLl.visibility = View.VISIBLE
        noDataTv.visibility = View.GONE
    }


}