package com.wanandroid.kotlin.ui.home

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
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.model.bean.Article
import com.wanandroid.kotlin.model.bean.HomeBannerBean
import com.wanandroid.kotlin.ui.ArticleDetailWebViewActivity
import com.wanandroid.kotlin.ui.ArticleListAdapter
import com.wanandroid.kotlin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mArticleListData: ArrayList<Article> = ArrayList()
    private var mHomeBannerBeanListData: ArrayList<HomeBannerBean> = ArrayList()
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

        homeViewModel.homeBannerBeanListLd.observe(viewLifecycleOwner, Observer {
            mHomeBannerBeanListData.clear()
            mHomeBannerBeanListData.addAll(it)
            homeBannerVp2.adapter = ViewPagerAdapter(this, mHomeBannerBeanListData)
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
        homeBannerBeanList: ArrayList<HomeBannerBean>
    ) :
        RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
        private var homeBannerBeanList: ArrayList<HomeBannerBean> = ArrayList()
        private var weakReference: WeakReference<HomeFragment>

        init {
            this.homeBannerBeanList = homeBannerBeanList
            this.weakReference = WeakReference(homeFragment)
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val bannerItemIv: ImageView = itemView.findViewById(R.id.bannerItemIv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_view_page_item, parent, false)
            return MyViewHolder(view)
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