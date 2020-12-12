package com.wanandroid.kotlin.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.model.bean.ProjectTreeItemBean
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectTypeFragment : Fragment() {

    private lateinit var projectVm: ProjectViewModel
    private var mProjectTreeItemBeans: ArrayList<ProjectTreeItemBean> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        projectVm = ViewModelProvider(this)[ProjectViewModel::class.java]
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectVm.projectTreeItemsLdBean.observe(viewLifecycleOwner, Observer {
            mProjectTreeItemBeans.clear()
            mProjectTreeItemBeans.addAll(it)

            initTabLayoutAndVp2()
        })
        projectVm.setProjectTreeItems()
    }

    private fun initTabLayoutAndVp2() {
        projectVp2.adapter = CompleteProjectVp2Adapter(this)
        //bind TabLayout to ViewPager2
        TabLayoutMediator(projectTabLayout, projectVp2) { tab, position ->
            tab.text = mProjectTreeItemBeans[position].name
        }.attach()
    }

    inner class CompleteProjectVp2Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return mProjectTreeItemBeans.size
        }

        override fun createFragment(position: Int): Fragment {
            return ProjectItemListFragment.newInstance(mProjectTreeItemBeans[position].id)
        }

    }
}