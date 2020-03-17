package com.jere.wanandroid_learning_kotlin.ui.complete_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.jere.wanandroid_learning_kotlin.R

class ArticleDetailWebViewActivity : AppCompatActivity() {
    companion object {
        const val ARTICLE_DETAIL_WEB_LINK_KEY = "ARTICLE_DETAIL_WEB_LINK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail_web_view)

        val link: String? = intent.getStringExtra(ARTICLE_DETAIL_WEB_LINK_KEY)

        val webViewModel: WebView = findViewById(R.id.article_detail_web_view)
        webViewModel.loadUrl(link)
    }
}
