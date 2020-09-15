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

        completeProjectVm.projectTreeItemsLd.observe(viewLifecycleOwner, Observer {
            mProjectTreeItems.clear()
            mProjectTreeItems.addAll(it)

            initTabLayoutAndVp2()
        })
        completeProjectVm.setProjectTreeItems()
    }

    private fun initTabLayoutAndVp2() {
        completeProjectVp2.adapter = CompleteProjectVp2Adapter(this)
        //bind TabLayout to ViewPager2
        TabLayoutMediator(completeProjectTabLayout, completeProjectVp2) { tab, position ->
            tab.text = mProjectTreeItems[position].name
        }.attach()
    }

    inner class CompleteProjectVp2Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return mProjectTreeItems.size
        }

        override fun createFragment(position: Int): Fragment {
            return ProjectItemListFragment.newInstance(mProjectTreeItems[position].id)
        }

    }
}