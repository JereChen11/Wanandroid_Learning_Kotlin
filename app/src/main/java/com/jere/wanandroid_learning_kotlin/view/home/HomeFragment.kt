package com.jere.wanandroid_learning_kotlin.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBanner
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.view.login.LoginActivity
import com.jere.wanandroid_learning_kotlin.viewmodel.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mArticleListData: ArrayList<Article> = ArrayList()
    private var mHomeBannerListData: ArrayList<HomeBanner> = ArrayList()
    private lateinit var mHomeBannerHandler: HomeBannerHandler
    private lateinit var mHomeBannerExecutorService: ScheduledExecutorService
    private var pageNumber = 0
    private lateinit var articleListAdapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHomeBannerHandler = HomeBannerHandler(this)

        initHomeBannerViewPager()
        initHomeArticleListRecyclerView()

        homeNsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            if (!v.canScrollVertically(1)) {
                pageNumber++
                homeViewModel.setHomeArticleList(pageNumber)
            }
        })


        startAutoLoopBanner()
    }

    private fun initHomeBannerViewPager() {
        val indicateViews =
            arrayOf(firstIndicateView, secondIndicateView, thirdIndicateView, fourthIndicateView)

        homeBannerVp2
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
                        indicateViews[i].setBackgroundResource(R.drawable.banner_navigation_point_highlight_shape)
                    } else {
                        indicateViews[i].setBackgroundResource(R.drawable.banner_navigation_point_gray_shape)
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

        homeViewModel.homeBannerListLd.observe(viewLifecycleOwner, Observer {
            mHomeBannerListData.clear()
            mHomeBannerListData.addAll(it)
            homeBannerVp2.adapter = ViewPagerAdapter(this, mHomeBannerListData)
            homeBannerVp2.currentItem = 1
        })

        homeViewModel.setHomeBannerList()
    }

    private fun initHomeArticleListRecyclerView() {
        articleListAdapter = ArticleListAdapter(mArticleListData,  object : ArticleListAdapter.AdapterItemClickListener {
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

            override fun clickWithoutLogin() {
                startActivity(Intent(activity, LoginActivity::class.java))
            }

        })
        homeArticleListRcy.adapter = articleListAdapter

        homeViewModel.articleListLd.observe(viewLifecycleOwner, Observer {
            mArticleListData.addAll(it.articles)
            articleListAdapter.setData(mArticleListData)
        })

        homeViewModel.setHomeArticleList(pageNumber)
    }

    private fun startAutoLoopBanner() {
        mHomeBannerExecutorService = Executors.newScheduledThreadPool(1)
        mHomeBannerExecutorService.scheduleAtFixedRate({
            val msg = Message()
            msg.what = 1
            mHomeBannerHandler.sendMessage(msg)
        }, 2, 5, TimeUnit.SECONDS)
    }

    fun toRealPosition(position: Int, realCount: Int): Int {
        var realPosition = 0
        if (realCount > 1) {
            realPosition = (position - 1) % realCount
        }
        if (realPosition < 0) {
            realPosition += realCount
        }
        return realPosition
    }

    inner class ViewPagerAdapter(
        homeFragment: HomeFragment,
        homeBannerList: ArrayList<HomeBanner>
    ) :
        RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
        private var homeBannerList: ArrayList<HomeBanner> = ArrayList()
        private var weakReference: WeakReference<HomeFragment>

        init {
            this.homeBannerList = homeBannerList
            this.weakReference = WeakReference(homeFragment)
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val bannerItemIv: ImageView = itemView.findViewById(R.id.banner_item_iv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_view_page_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            if (homeBannerList.size > 0) {
                //为了实现Banner循环轮播，需要额外多两张图片，分别放置列表首尾。
                return homeBannerList.size + 2
            }
            return homeBannerList.size
        }

        private fun getRealCount(): Int {
            return homeBannerList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: HomeBanner =
                homeBannerList[toRealPosition(position, getRealCount())]
            weakReference.get()
                ?.let { Glide.with(it).load(data.imagePath).into(holder.bannerItemIv) }

            val articleDetailUrl: String? = data.url
            holder.bannerItemIv.setOnClickListener {
                val intent =
                    Intent(weakReference.get()?.activity, ArticleDetailWebViewActivity::class.java)
                intent.putExtra(
                    ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                    articleDetailUrl
                )
                weakReference.get()?.startActivity(intent)
            }
        }
    }

    class HomeBannerHandler(homeFragment: HomeFragment) : Handler() {
        private val weakReference: WeakReference<HomeFragment> = WeakReference(homeFragment)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                val homeFragment: HomeFragment? = weakReference.get()
                if (homeFragment?.homeBannerVp2 != null) {
                    val vpCurrentPosition: Int = homeFragment.homeBannerVp2.currentItem
                    if (vpCurrentPosition == 4) {
                        homeFragment.homeBannerVp2.currentItem = 1
                    } else {
                        homeFragment.homeBannerVp2.currentItem = vpCurrentPosition + 1
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mHomeBannerExecutorService.isShutdown) {
            mHomeBannerExecutorService.shutdown()
        }
    }
}