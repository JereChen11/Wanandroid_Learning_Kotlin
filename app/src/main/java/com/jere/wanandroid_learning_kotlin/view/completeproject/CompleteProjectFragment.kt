package com.jere.wanandroid_learning_kotlin.view.completeproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem
import com.jere.wanandroid_learning_kotlin.utils.RecyclerItemClickListener
import com.jere.wanandroid_learning_kotlin.viewmodel.completeproject.CompleteProjectViewModel

class CompleteProjectFragment : Fragment() {

    private lateinit var completeProjectVm: CompleteProjectViewModel
    private var mProjectTreeItems: ArrayList<ProjectTreeItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        completeProjectVm = ViewModelProvider(this)[CompleteProjectViewModel::class.java]
        return inflater.inflate(R.layout.fragment_complete_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val completeProjectRv: RecyclerView = view.findViewById(R.id.complete_project_rv)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 2)
        completeProjectRv.layoutManager = layoutManager
        completeProjectRv.addOnItemTouchListener(RecyclerItemClickListener(context,
            completeProjectRv, object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    Toast.makeText(context, "position = $position", Toast.LENGTH_SHORT).show()
                    val cid = mProjectTreeItems[position].id
                    val intent = Intent(context, ProjectItemListActivity::class.java)
                    intent.putExtra(ProjectItemListActivity.PROJECT_TREE_ITEM_ID_KEY, cid)
                    startActivity(intent)
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    Toast.makeText(
                        context,
                        "long Item click position = $position",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ))

        completeProjectVm.projectTreeItemsLd.observe(viewLifecycleOwner, Observer {
            mProjectTreeItems.clear()
            mProjectTreeItems.addAll(it)
            completeProjectRv.adapter = MyAdapter(mProjectTreeItems)
        })
        completeProjectVm.setProjectTreeItems()
    }

    class MyAdapter(projectItems: ArrayList<ProjectTreeItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private var projectItems: ArrayList<ProjectTreeItem> = ArrayList()

        init {
            this.projectItems = projectItems
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val contentTv: TextView = itemView.findViewById(R.id.complete_project_content_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_view_complete_project, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return projectItems.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: ProjectTreeItem = projectItems[position]
            holder.contentTv.text = data.name
        }
    }
}