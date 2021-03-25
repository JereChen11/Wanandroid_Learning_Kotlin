package com.wanandroid.kotlin.ui.home

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.bean.HomeBannerBean
import com.wanandroid.kotlin.data.repository.HomeRepository
import com.wanandroid.kotlin.databinding.BannerViewPageItemBinding
import com.wanandroid.kotlin.databinding.FragmentHomeBinding
import com.wanandroid.kotlin.ui.adapter.ArticleListAdapter
import com.wanandroid.kotlin.ui.base.BaseVmFragment
import com.wanandroid.kotlin.ui.detail.ArticleDetailWebViewActivity
import com.wanandroid.kotlin.ui.login.LoginActivity
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class HomeFragment : BaseVmFragment<HomeViewModel, FragmentHomeBinding>() {

    private var mArticleListData: ArrayList<Article> = ArrayList()
    private var mHomeBannerBeanListData: ArrayList<HomeBannerBean> = ArrayList()
    private lateinit var mHomeBannerHandler: HomeBannerHandler
    private lateinit var mHomeBannerExecutorService: ScheduledExecutorService
    private var pageNumber = 0
    private lateinit var articleListAdapter: ArticleListAdapter

    override fun setVmFactory(): ViewModelProvider.Factory = HomeVmFactory(HomeRepository())

    override fun initData() {
        viewModel.setHomeBannerList()
        viewModel.setHomeArticleList(pageNumber)
    }

    override fun initView() {
        mHomeBannerHandler = HomeBannerHandler(this)

        initHomeBannerViewPager()
        initHomeArticleListRecyclerView()

        binding.homeNsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            if (!v.canScrollVertically(1)) {
                pageNumber++
                viewModel.setHomeArticleList(pageNumber)
            }
        })

        startAutoLoopBanner()
    }

    override fun initObserve() {
        viewModel.articleListLd.observe(viewLifecycleOwner, Observer {
            mArticleListData.addAll(it.articles)
            articleListAdapter.setData(mArticleListData)
        })

        viewModel.homeBannerBeanListLd.observe(viewLifecycleOwner, Observer {
            mHomeBannerBeanListData.clear()
            mHomeBannerBeanListData.addAll(it)
            binding.homeBannerVp2.apply {
                adapter = ViewPagerAdapter(mHomeBannerBeanListData)
                currentItem = 1
            }

        })
    }

    private fun initHomeBannerViewPager() {
        binding.apply {
            val indicateViews =
                arrayOf(
                    firstIndicateView,
                    secondIndicateView,
                    thirdIndicateView,
                    fourthIndicateView
                )

            homeBannerVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    var bannerPosition = position
                    if (bannerPosition == 5) {
                        homeBannerVp2.setCurrentItem(1, false)
                    } else if (bannerPosition == 0) {
                        homeBannerVp2.setCurrentItem(4, false)
                    }
                    bannerPosition = toRealPosition(bannerPosition, 4)
                    for (i in 0..3) {
                        if (bannerPosition == i) {
                            indicateViews[i].setBackgroundResource(R.drawable.shape_banner_nav_point_highlight_oval_white)
                        } else {
                            indicateViews[i].setBackgroundResource(R.drawable.shape_banner_nav_point_oval_gray)
                        }
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == ViewPager2.SCROLL_STATE_DRAGGING && !mHomeBannerExecutorService.isShutdown) {
                        mHomeBannerExecutorService.shutdown()
                    }
                }
            })
        }

    }

    private fun initHomeArticleListRecyclerView() {
        articleListAdapter = ArticleListAdapter(
            requireContext(),
            mArticleListData,
            object :
                ArticleListAdapter.AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = mArticleListData[position].link
                    val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
                    intent.putExtra(
                        ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                        link
                    )
                    startActivity(intent)
                }

                override fun onLongClicked(v: View?, position: Int) {
                }

            })
        binding.homeArticleListRcy.adapter = articleListAdapter

    }

    private fun startAutoLoopBanner() {
        mHomeBannerExecutorService = Executors.newScheduledThreadPool(1)
        mHomeBannerExecutorService.scheduleAtFixedRate({
            val msg = Message()
            msg.what = 1
            mHomeBannerHandler.sendMessage(msg)
        }, 2, 5, TimeUnit.SECONDS)
    }

    class ViewPagerAdapter(
        private var homeBannerBeanList: ArrayList<HomeBannerBean>
    ) :
        RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

        class MyViewHolder(private val binding: BannerViewPageItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(homeBannerBean: HomeBannerBean) {
                val context = binding.root.context
                Glide.with(context).load(homeBannerBean.imagePath).into(binding.bannerItemIv)

                val articleDetailUrl: String? = homeBannerBean.url
                binding.bannerItemIv.setOnClickListener {
                    val intent = Intent(context, ArticleDetailWebViewActivity::class.java)
                    intent.putExtra(
                        ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                        articleDetailUrl
                    )
                    context.startActivity(intent)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding = BannerViewPageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MyViewHolder(binding)
        }

        override fun getItemCount(): Int {
            if (homeBannerBeanList.size > 0) {
                //为了实现Banner循环轮播，需要额外多两张图片，分别放置列表首尾。
                return homeBannerBeanList.size + 2
            }
            return homeBannerBeanList.size
        }

        private fun getRealCount(): Int {
            return homeBannerBeanList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: HomeBannerBean =
                homeBannerBeanList[toRealPosition(position, getRealCount())]

            holder.bind(data)

        }
    }

    inner class HomeBannerHandler(homeFragment: HomeFragment) : Handler() {
        private val weakReference: WeakReference<HomeFragment> = WeakReference(homeFragment)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                weakReference.get()?.binding?.homeBannerVp2?.apply {
                    val vpCurrentPosition: Int = currentItem
                    currentItem = if (vpCurrentPosition == 4) 1 else vpCurrentPosition + 1
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!mHomeBannerExecutorService.isShutdown) {
            mHomeBannerExecutorService.shutdown()
        }
    }

    companion object {
        private fun toRealPosition(position: Int, realCount: Int): Int {
            var realPosition = 0
            if (realCount > 1) {
                realPosition = (position - 1) % realCount
            }
            if (realPosition < 0) {
                realPosition += realCount
            }
            return realPosition
        }
    }
}