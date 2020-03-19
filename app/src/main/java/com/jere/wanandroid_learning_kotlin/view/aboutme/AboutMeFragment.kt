package com.jere.wanandroid_learning_kotlin.view.aboutme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.jere.wanandroid_learning_kotlin.R

class AboutMeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aboutMeWebView: WebView = view.findViewById(R.id.about_me_wb)
        aboutMeWebView.loadUrl("https://blog.csdn.net/jerechen")
    }
}