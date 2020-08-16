package com.jere.wanandroid_learning_kotlin.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.jere.wanandroid_learning_kotlin.R
import kotlinx.android.synthetic.main.custom_view_pull_up_refresh_view.view.*

class PullUpRefreshView(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    init {
        init(context)
    }

    private fun init(context: Context?) {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.custom_view_pull_up_refresh_view, this, true)

    }

    fun showNoData() {
        loadingContainerLl.visibility = View.GONE
        noDataTv.visibility = View.VISIBLE
    }

    fun showLoadingData() {
        loadingContainerLl.visibility = View.VISIBLE
        noDataTv.visibility = View.GONE
    }


}