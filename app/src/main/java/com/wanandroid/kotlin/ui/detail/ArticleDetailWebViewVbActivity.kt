package com.wanandroid.kotlin.ui.detail

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.databinding.ActivityArticleDetailWebViewBinding
import com.wanandroid.kotlin.ui.base.BaseVmVbActivity


class ArticleDetailWebViewVbActivity :
    BaseVmVbActivity<ArticleDetailViewModel, ActivityArticleDetailWebViewBinding>() {
    companion object {
        const val ARTICLE_DETAIL_WEB_LINK_KEY = "ARTICLE_DETAIL_WEB_LINK"
    }

    override fun setVmFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ArticleDetailViewModel() as T
            }
        }
    }

    override fun initView() {
        val link: String? = intent.getStringExtra(ARTICLE_DETAIL_WEB_LINK_KEY)
        Log.e(TAG, "initView: link = $link")

        val webView: WebView = findViewById(R.id.articleDetailWebView)
        webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = MyWebViewClient()
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            link?.let { linkString ->
                loadUrl(linkString)
            }
        }

    }

    inner class MyWebViewClient : WebViewClient() {

        /**
         * true -> 表示用 webView 打开
         * false -> 表示调用三方
         */
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url.isNullOrEmpty()) {
                return false
            }

//            if (url.startsWith("jianshu://")) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//            }

            if (appInstalledOrNot(url)) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            return true
        }

        private fun appInstalledOrNot(uri: String): Boolean {
            val pm = packageManager
            return try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                Log.e(TAG, "appInstalledOrNot: ${e.message}")
                false
            }
        }

    }


}
