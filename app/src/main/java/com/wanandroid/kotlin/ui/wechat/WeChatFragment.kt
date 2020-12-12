package com.wanandroid.kotlin.ui.wechat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.model.bean.Article
import com.wanandroid.kotlin.model.bean.WeChatBean
import com.wanandroid.kotlin.ui.ArticleDetailWebViewActivity
import com.wanandroid.kotlin.ui.ArticleListAdapter
import com.wanandroid.kotlin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_we_chat.*

class WeChatFragment : Fragment() {

    private lateinit var weChatVm: WeChatViewModel
    private var mWeChatBean: ArrayList<WeChatBean> = ArrayList()
    private var mWeChatArticleList: ArrayList<Article> = ArrayList()
    private var currentSelectedBloggerId = 0
    private var pageNumber = 0
    private lateinit var weChatVp2Adapter: WeChatVp2Adapter
    private lateinit var articleListAdapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weChatVm = ViewModelProvider(this)[WeChatViewModel::class.java]
        return inflater.inflate(R.layout.fragment_we_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleListAdapter = ArticleListAdapter(mWeChatArticleList, object :
            ArticleListAdapter.AdapterItemClickListener {
            override fun onPositionClicked(v: View?, position: Int) {
                if (mWeChatArticleList.size > 0) {
                    val link: String? = mWeChatArticleList[position].link
                    val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
                    intent.putExtra(ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY, link)
                    startActivity(intent)
                }
            }

            override fun onLongClicked(v: View?, position: Int) {
            }

            override fun clickWithoutLogin() {
                startActivity(Intent(activity, LoginActivity::class.java))
            }

        })

        weChatVp2Adapter = WeChatVp2Adapter(
            mWeChatBean,
            articleListAdapter,
            object : WeChatVp2Adapter.ScrollListener {
                override fun isScrollBottom(isBottom: Boolean) {
                    pageNumber++
                    weChatVm.setWeChatArticleListLd(currentSelectedBloggerId, pageNumber)
//                    weChatPullUpRefreshView.showLoadingData()
                }
            })

        weChatVp2.adapter = weChatVp2Adapter

        TabLayoutMediator(weChatBloggerTabLayout, weChatVp2) { tab, position ->
            tab.text = mWeChatBean[position].name
            weChatVp2.setCurrentItem(tab.position, true)
        }.attach()

        weChatVm.weChatBeanLd.observe(viewLifecycleOwner, Observer {
            mWeChatBean.clear()
            mWeChatBean.addAll(it)

            weChatVp2Adapter.setWeChatBloggerData(mWeChatBean)

        })

        weChatVm.weChatArticleListLd.observe(viewLifecycleOwner, Observer {
//            if (weChatArticleSrl.isRefreshing) {
//                weChatArticleSrl.isRefreshing = false
//            }
            mWeChatArticleList.addAll(it.articles)

            val newWeChatArticleList: ArrayList<Article> = ArrayList()
            newWeChatArticleList.addAll(mWeChatArticleList)

            articleListAdapter.setData(newWeChatArticleList)
        })

        weChatVm.setWeChatBloggerListLd()

        weChatBloggerTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                weChatArticleSrl.isRefreshing = true
                currentSelectedBloggerId = mWeChatBean[tab?.position!!].id
                pageNumber = 0
                weChatVm.setWeChatArticleListLd(currentSelectedBloggerId, pageNumber)
                mWeChatArticleList.clear()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

        })

//        weChatArticleSrl.setOnRefreshListener {
//            weChatVm.setWeChatArticleListLd(currentSelectedBloggerId, pageNumber)
//            pageNumber = 0
//            mWeChatArticleList.clear()
//        }

    }

    class WeChatVp2Adapter(
        private var weChatBean: ArrayList<WeChatBean>,
        private var weChatArticleListAdapter: ArticleListAdapter,
        private val scrollListener: ScrollListener
    ) :
        RecyclerView.Adapter<WeChatVp2Adapter.MyViewHolder>() {

        interface ScrollListener {
            fun isScrollBottom(isBottom: Boolean)
        }

        fun setWeChatBloggerData(weChatBean: ArrayList<WeChatBean>) {
            this.weChatBean = weChatBean
            notifyDataSetChanged()
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val weChatArticleRcy: RecyclerView = itemView.findViewById(R.id.weChatArticleListRcy)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_view_wechat_article_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return weChatBean.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.weChatArticleRcy.adapter = weChatArticleListAdapter

            holder.weChatArticleRcy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        scrollListener.isScrollBottom(true)
                    }
                }
            })
        }
    }
}