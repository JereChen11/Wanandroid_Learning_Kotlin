package com.wanandroid.kotlin.ui.detail

import android.content.Context
import android.os.Build
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.ui.base.BaseActivity

class ArticleDetailWebViewActivity : BaseActivity() {
    companion object {
        const val ARTICLE_DETAIL_WEB_LINK_KEY = "ARTICLE_DETAIL_WEB_LINK"
    }

    override fun bindLayout(): Int {
        return R.layout.activity_article_detail_web_view
    }

    override fun initView(view: View?) {
        val link: String? = intent.getStringExtra(ARTICLE_DETAIL_WEB_LINK_KEY)

        val webView: WebView = findViewById(R.id.articleDetailWebView)
        webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            }
            loadUrl(link)
        }

    }

    override fun doBusiness(mContext: Context?) {
    }
}
