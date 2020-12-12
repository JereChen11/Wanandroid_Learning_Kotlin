package com.wanandroid.kotlin.ui

import android.content.Context
import android.view.View
import android.webkit.WebView
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.utils.BaseActivity

class ArticleDetailWebViewActivity : BaseActivity() {
    companion object {
        const val ARTICLE_DETAIL_WEB_LINK_KEY = "ARTICLE_DETAIL_WEB_LINK"
    }

    override fun bindLayout(): Int {
        return R.layout.activity_article_detail_web_view
    }

    override fun initView(view: View?) {
        val link: String? = intent.getStringExtra(ARTICLE_DETAIL_WEB_LINK_KEY)

        val webViewModel: WebView = findViewById(R.id.articleDetailWebView)
        webViewModel.loadUrl(link)
    }

    override fun doBusiness(mContext: Context?) {
    }
}
