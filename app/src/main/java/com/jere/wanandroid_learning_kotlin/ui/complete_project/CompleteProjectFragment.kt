package com.jere.wanandroid_learning_kotlin.ui.complete_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jere.wanandroid_learning_kotlin.R

class CompleteProjectFragment : Fragment() {

    private lateinit var galleryViewModel: CompleteProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(CompleteProjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_complete_project, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}