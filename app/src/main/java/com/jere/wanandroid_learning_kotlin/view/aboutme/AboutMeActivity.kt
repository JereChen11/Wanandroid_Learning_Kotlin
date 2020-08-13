package com.jere.wanandroid_learning_kotlin.view.aboutme

import android.content.Context
import android.view.View
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMeActivity : BaseActivity() {
    override fun bindLayout(): Int {
        return R.layout.activity_about_me
    }

    override fun initView(view: View?) {
        aboutMeWeb.loadUrl("https://blog.csdn.net/jerechen")

    }

    override fun doBusiness(mContext: Context?) {
    }
}