package com.wanandroid.kotlin.ui.project

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wanandroid.kotlin.data.bean.ProjectTreeItemBean
import com.wanandroid.kotlin.data.repository.ProjectRepository
import com.wanandroid.kotlin.databinding.FragmentProjectBinding
import com.wanandroid.kotlin.ui.base.BaseVmFragment

class ProjectTypeFragment : BaseVmFragment<ProjectViewModel, FragmentProjectBinding>() {
    private var mProjectTreeItemBeans: ArrayList<ProjectTreeItemBean> = ArrayList()

    override fun setVmFactory(): ViewModelProvider.Factory = ProjectVmFactory(ProjectRepository())

    override fun initView() {
    }

    override fun initData() {
        viewModel.setProjectTreeItems()
    }

    override fun initObserve() {
        viewModel.projectTreeItemsLdBean.observe(viewLifecycleOwner, Observer {
            mProjectTreeItemBeans.clear()
            mProjectTreeItemBeans.addAll(it)

            initTabLayoutAndVp2()
        })
    }

    private fun initTabLayoutAndVp2() {
        binding.apply {
            projectVp2.adapter = CompleteProjectVp2Adapter(this@ProjectTypeFragment, mProjectTreeItemBeans)
            //bind TabLayout to ViewPager2
            TabLayoutMediator(projectTabLayout, projectVp2) { tab, position ->
                tab.text = mProjectTreeItemBeans[position].name
            }.attach()
        }
    }

    class CompleteProjectVp2Adapter(
        fragment: Fragment,
        private val projectTreeItemBeans: ArrayList<ProjectTreeItemBean>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return projectTreeItemBeans.size
        }

        override fun createFragment(position: Int): Fragment {
            return ProjectItemListFragment.newInstance(projectTreeItemBeans[position].id)
        }

    }
}