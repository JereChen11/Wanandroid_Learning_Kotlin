package com.jere.wanandroid_learning_kotlin.ui.wechart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jere.wanandroid_learning_kotlin.R

class WeChartFragment : Fragment() {

    private lateinit var slideshowViewModel: WeChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(WeChartViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_we_chart, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}