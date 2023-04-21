package com.wanandroid.kotlin.ui.wechat

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wanandroid.kotlin.data.bean.WeChatBean
import com.wanandroid.kotlin.data.repository.WeChatRepository
import com.wanandroid.kotlin.databinding.FragmentWeChatBinding
import com.wanandroid.kotlin.ui.base.BaseVmVbFragment

class WeChatFragment : BaseVmVbFragment<WeChatViewModel, FragmentWeChatBinding>() {

    private var mWeChatBean: ArrayList<WeChatBean> = ArrayList()

    override fun setVmFactory(): ViewModelProvider.Factory = WeChatVmFactory(WeChatRepository())

    override fun initData() {
        viewModel.getWeChatBloggerListLd()
    }

    override fun initView() {

    }

    override fun initObserve() {
        viewModel.weChatBeanLd.observe(viewLifecycleOwner, Observer {
            mWeChatBean.clear()
            mWeChatBean.addAll(it)

            initTabLayoutAndVp2()

        })
    }

    private fun initTabLayoutAndVp2() {
        binding.apply {
            weChatVp2.adapter = WeChatVp2Adapter(this@WeChatFragment, mWeChatBean)
            //bind TabLayout to ViewPager2
            TabLayoutMediator(weChatBloggerTabLayout, weChatVp2) { tab, position ->
                tab.text = mWeChatBean[position].name
            }.attach()
        }
    }

    class WeChatVp2Adapter(
        fragment: Fragment,
        private var weChatBeanList: ArrayList<WeChatBean>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return weChatBeanList.size
        }

        override fun createFragment(position: Int): Fragment {
            return WeChatArticleListFragment.newInstance(weChatBeanList[position].id)
        }
    }
}