package com.jere.wanandroid_learning_kotlin.view.wechat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.ArticleList
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBlogger
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.viewmodel.wechat.WeChatViewModel
import kotlinx.android.synthetic.main.fragment_we_chat.*

class WeChatFragment : Fragment() {

    private lateinit var weChatVm: WeChatViewModel
    private var mWeChatBlogger: ArrayList<WeChatBlogger> = ArrayList()
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
                val link: String? = mWeChatArticleList[position].link
                val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
                intent.putExtra(ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY, link)
                startActivity(intent)
            }

            override fun onLongClicked(v: View?, position: Int) {
            }

        })
        weChatVp2Adapter = WeChatVp2Adapter(mWeChatBlogger, articleListAdapter)
        weChatVp2.adapter = weChatVp2Adapter

        TabLayoutMediator(weChatBloggerTabLayout, weChatVp2) { tab, position ->
            tab.text = mWeChatBlogger[position].name
            weChatVp2.setCurrentItem(tab.position, true)
        }.attach()

        weChatVm.weChatBloggerLd.observe(viewLifecycleOwner, Observer {
            mWeChatBlogger.clear()
            mWeChatBlogger.addAll(it)

            weChatVp2Adapter.setWeChatBloggerData(mWeChatBlogger)

        })

        weChatVm.weChatArticleListLd.observe(viewLifecycleOwner, Observer {
            mWeChatArticleList.clear()
            mWeChatArticleList.addAll(it.articles)

            articleListAdapter.setData(mWeChatArticleList)
        })

        weChatVm.setWeChatBloggerListLd()

        weChatBloggerTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentSelectedBloggerId = mWeChatBlogger[tab?.position!!].id
                weChatVm.setWeChatArticleListLd(currentSelectedBloggerId, pageNumber)
                mWeChatArticleList.clear()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

        })

//        weChatNsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (!v.canScrollVertically(1)) {
//                pageNumber++
//                weChatVm.setWeChatArticleListLd(currentSelectedBloggerId, pageNumber)
//            }
//        })

    }

    class WeChatVp2Adapter(private var weChatBlogger: ArrayList<WeChatBlogger>,
                           private var weChatArticleListAdapter: ArticleListAdapter) :
        RecyclerView.Adapter<WeChatVp2Adapter.MyViewHolder>() {

        fun setWeChatBloggerData(weChatBlogger: ArrayList<WeChatBlogger>) {
            this.weChatBlogger = weChatBlogger
            notifyDataSetChanged()
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val weChatArticleRcy: RecyclerView = itemView.findViewById(R.id.weChatArticleListRcy)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_list_wechat_article_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return weChatBlogger.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.weChatArticleRcy.adapter = weChatArticleListAdapter
        }
    }
}