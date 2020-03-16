package com.jere.wanandroid_learning_kotlin.ui.complete_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R

class CompleteProjectFragment : Fragment() {

    private lateinit var galleryViewModel: CompleteProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProviders.of(this).get(CompleteProjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_complete_project, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val completeProjectRv: RecyclerView = view.findViewById(R.id.complete_project_rv)

        val projectItems: ArrayList<String> = ArrayList()
        projectItems.add("first")
        projectItems.add("second")
        projectItems.add("third")
        projectItems.add("third")
        projectItems.add("third")
        completeProjectRv.adapter = MyAdapter(projectItems)
    }

    class MyAdapter(projectItems: ArrayList<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private var projectItems: ArrayList<String> = ArrayList()

        init {
            this.projectItems = projectItems
        }


        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val contentTv = itemView.findViewById<TextView>(R.id.content_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view_complete_project, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return projectItems.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val string: String = projectItems[position]
            holder.contentTv.text = string
        }
    }
}