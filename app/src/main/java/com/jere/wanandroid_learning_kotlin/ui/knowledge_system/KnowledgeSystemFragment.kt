package com.jere.wanandroid_learning_kotlin.ui.knowledge_system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jere.wanandroid_learning_kotlin.R

class KnowledgeSystemFragment : Fragment() {

    private lateinit var toolsViewModel: KnowledgeSystemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(KnowledgeSystemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_knowledge_system, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}