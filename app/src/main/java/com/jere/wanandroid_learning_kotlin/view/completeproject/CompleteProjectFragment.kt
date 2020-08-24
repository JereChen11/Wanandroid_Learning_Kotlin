package com.jere.wanandroid_learning_kotlin.view.completeproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.completeprojectbeanfiles.ProjectTreeItem
import com.jere.wanandroid_learning_kotlin.viewmodel.completeproject.CompleteProjectViewModel
import kotlinx.android.synthetic.main.fragment_complete_project.*

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

//        val completeProjectRv: RecyclerView = view.findViewById(R.id.complete_project_rv)
//        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 2)
//        completeProjectRv.layoutManager = layoutManager
//        completeProjectRv.addOnItemTouchListener(RecyclerItemClickListener(context,
//            completeProjectRv, object : RecyclerItemClickListener.OnItemClickListener {
//                override fun onItemClick(view: View?, position: Int) {
//
//                    Toast.makeText(context, "position = $position", Toast.LENGTH_SHORT).show()
//                    val cid = mProjectTreeItems[position].id
//                    val intent = Intent(context, ProjectItemListActivity::class.java)
//                    intent.putExtra(ProjectItemListActivity.PROJECT_TREE_ITEM_ID_KEY, cid)
//                    startActivity(intent)
//                }
//
//                override fun onLongItemClick(view: View?, position: Int) {
//                    Toast.makeText(
//                        context,
//                        "long Item click position = $position",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        ))

        completeProjectVm.projectTreeItemsLd.observe(viewLifecycleOwner, Observer {
            mProjectTreeItems.clear()
            mProjectTreeItems.addAll(it)

            initTabLayoutAndVp2()

//            completeProjectRv.adapter = MyAdapter(mProjectTreeItems)
        })
        completeProjectVm.setProjectTreeItems()


//        completeProjectTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

    private fun initTabLayoutAndVp2() {
        completeProjectVp2.adapter = CompleteProjectVp2Adapter(this)
        //bind TabLayout to ViewPager2
        TabLayoutMediator(completeProjectTabLayout, completeProjectVp2) { tab, position ->
            tab.text = mProjectTreeItems[position].name
        }.attach()
    }

//    class MyAdapter(projectItems: ArrayList<ProjectTreeItem>) :
//        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//        private var projectItems: ArrayList<ProjectTreeItem> = ArrayList()
//
//        init {
//            this.projectItems = projectItems
//        }
//
//        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val contentTv: TextView = itemView.findViewById(R.id.complete_project_content_tv)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//            val view: View =
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.recycler_item_view_complete_project, parent, false)
//            return MyViewHolder(view)
//        }
//
//        override fun getItemCount(): Int {
//            return projectItems.size
//        }
//
//        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//            val data: ProjectTreeItem = projectItems[position]
//            holder.contentTv.text = data.name
//        }
//    }

    inner class CompleteProjectVp2Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return mProjectTreeItems.size
        }

        override fun createFragment(position: Int): Fragment {
            return ProjectItemListFragment.newInstance(mProjectTreeItems[position].id)
        }

    }
}