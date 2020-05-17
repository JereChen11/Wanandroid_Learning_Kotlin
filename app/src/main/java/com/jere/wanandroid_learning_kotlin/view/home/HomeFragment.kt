package com.jere.wanandroid_learning_kotlin.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBannerListBean
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter.AdapterItemClickListener
import com.jere.wanandroid_learning_kotlin.viewmodel.home.HomeViewModel
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mArticleListData: ArrayList<ArticleListBean.DataBean.DatasBean> =
        ArrayList()
    private var mHomeBannerListData: ArrayList<HomeBannerListBean.DataBean> = ArrayList()
    private lateinit var mHomeBannerVp: ViewPager2
    private lateinit var mHomeBannerHandler: HomeBannerHandler
    private lateinit var mHomeBannerExecutorService: ScheduledExecutorService

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

        initHomeBannerViewPager(view)
        initHomeArticleListRecyclerView(view)

        startAutoLoopBanner()
    }

    private fun initHomeBannerViewPager(view: View) {
        val firstView: View = view.findViewById(R.id.first_indicate_view)
        val secondView: View = view.findViewById(R.id.second_indicate_view)
        val thirdView: View = view.findViewById(R.id.third_indicate_view)
        val fourthView: View = view.findViewById(R.id.fourth_indicate_view)
        val indicateViews = arrayOf(firstView, secondView, thirdView, fourthView)

        mHomeBannerVp = view.findViewById(R.id.home_banner_vp2)
        mHomeBannerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                var bannerPosition = position
                //change view page position, if position on banner head and end.
                if (bannerPosition == 0) {
                    bannerPosition = 4
                } else if (bannerPosition == 5) {
                    bannerPosition = 1
                }

                for (i in 1..4) {
                    if (bannerPosition == i) {
                        indicateViews[i - 1].setBackgroundResource(R.drawable.banner_navigation_point_highlight_shape)
                    } else {
                        indicateViews[i - 1].setBackgroundResource(R.drawable.banner_navigation_point_gray_shape)
                    }
                }

                //if bannerPosition != position, meaning you are in the banner head or end,
                // you need to change viewPager position to implement banner loop feature
                if (bannerPosition != position) {
                    mHomeBannerVp.currentItem = bannerPosition
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
            mHomeBannerListData = ArrayList()
            //add two fake data on list head and end to implement banner loop feature.
            val emptyHomeBannerData: HomeBannerListBean.DataBean = HomeBannerListBean.DataBean()
            mHomeBannerListData.add(emptyHomeBannerData)
            mHomeBannerListData.addAll(it)
            mHomeBannerListData.add(emptyHomeBannerData)
            mHomeBannerVp.adapter = ViewPagerAdapter(this, mHomeBannerListData)
            mHomeBannerVp.currentItem = 1
        })

        homeViewModel.setHomeBannerList()
    }

    private fun initHomeArticleListRecyclerView(view: View) {
        val homeArticleListRecyclerView: RecyclerView =
            view.findViewById(R.id.home_article_list_recycle_view)

        homeViewModel.articleListLd.observe(viewLifecycleOwner, Observer {
            mArticleListData = it
            val adapter = ArticleListAdapter(it, object : AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = it[position].link
                    val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
                    intent.putExtra(ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY, link)
                    startActivity(intent)
                }

                override fun onLongClicked(v: View?, position: Int) {
                }

            })
            homeArticleListRecyclerView.adapter = adapter
        })

        homeViewModel.setHomeArticleList(1)
    }

    private fun startAutoLoopBanner() {
        mHomeBannerExecutorService = Executors.newScheduledThreadPool(1)
        mHomeBannerExecutorService.scheduleAtFixedRate({
            val msg = Message()
            msg.what = 1
            mHomeBannerHandler.sendMessage(msg)
        }, 2, 3, TimeUnit.SECONDS)
    }

    class ViewPagerAdapter(
        homeFragment: HomeFragment,
        homeBannerList: ArrayList<HomeBannerListBean.DataBean>
    ) :
        RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
        private var homeBannerList: ArrayList<HomeBannerListBean.DataBean> = ArrayList()
        private var weakReference: WeakReference<HomeFragment>

        init {
            this.homeBannerList = homeBannerList
            this.weakReference = WeakReference(homeFragment)
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val bannerItemIv: ImageView = itemView.findViewById(R.id.banner_item_iv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_view_page_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return homeBannerList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: HomeBannerListBean.DataBean = homeBannerList[position]
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
                if (homeFragment != null) {
                    val vpCurrentPosition: Int = homeFragment.mHomeBannerVp.currentItem
                    if (vpCurrentPosition == 4) {
                        homeFragment.mHomeBannerVp.currentItem = 1
                    } else {
                        homeFragment.mHomeBannerVp.currentItem = vpCurrentPosition + 1
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