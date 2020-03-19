package com.jere.wanandroid_learning_kotlin.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeArticleListBean
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeBannerListBean
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.utils.RecyclerItemClickListener
import com.jere.wanandroid_learning_kotlin.viewmodel.home.HomeViewModel
import java.lang.ref.WeakReference


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mHomeArticleListData: ArrayList<HomeArticleListBean.DataBean.DatasBean> =
        ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeBannerVp2: ViewPager2 = view.findViewById(R.id.home_banner_vp2)
        val homeArticleListRecyclerView: RecyclerView =
            view.findViewById(R.id.home_article_list_recycle_view)


        homeViewModel.homeBannerListLd.observe(viewLifecycleOwner, Observer {
            homeBannerVp2.adapter = ViewPagerAdapter(this, it)
        })

        homeViewModel.homeArticleListLd.observe(viewLifecycleOwner, Observer {
            mHomeArticleListData = it
            homeArticleListRecyclerView.adapter = ArticleListAdapter(it)

        })

        homeViewModel.setHomeBannerList()
        homeViewModel.setHomeArticleList(1)


        val firstView: View = view.findViewById(R.id.first_indicate_view)
        val secondView: View = view.findViewById(R.id.second_indicate_view)
        val thirdView: View = view.findViewById(R.id.third_indicate_view)
        val fourthView: View = view.findViewById(R.id.fourth_indicate_view)
        val indicateViews = arrayOf(firstView, secondView, thirdView, fourthView)

        homeBannerVp2.currentItem = 0
        homeBannerVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0..3) {
                    if (position == i) {
                        indicateViews[i]
                            .setBackgroundResource(R.drawable.banner_navigation_point_highlight_shape)
                    } else {
                        indicateViews[i]
                            .setBackgroundResource(R.drawable.banner_navigation_point_gray_shape)
                    }
                }
            }
        })

        homeArticleListRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context,
                homeArticleListRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val link: String? = mHomeArticleListData[position].link
                        val intent = Intent(context, ArticleDetailWebViewActivity::class.java)
                        intent.putExtra(
                            ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                            link
                        )
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                    }

                })
        )

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
        }
    }

    class ArticleListAdapter(homeArticleList: ArrayList<HomeArticleListBean.DataBean.DatasBean>) :
        RecyclerView.Adapter<ArticleListAdapter.MyViewHolder>() {
        private var homeArticleList: ArrayList<HomeArticleListBean.DataBean.DatasBean> = ArrayList()

        init {
            this.homeArticleList = homeArticleList
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTv: TextView = itemView.findViewById(R.id.article_list_item_title_tv)
            val authorTv: TextView = itemView.findViewById(R.id.article_list_item_author_tv)
            val dateTv: TextView = itemView.findViewById(R.id.article_list_item_shared_date_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_view_home_article_list_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return homeArticleList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: HomeArticleListBean.DataBean.DatasBean = homeArticleList[position]
            holder.titleTv.text = data.title
            holder.authorTv.text = data.author
            holder.dateTv.text = data.niceShareDate
        }
    }


}