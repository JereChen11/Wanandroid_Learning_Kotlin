package com.wanandroid.kotlin.ui.aboutme

import android.content.Context
import android.view.View
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.utils.BaseActivity
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