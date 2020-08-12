package com.jere.wanandroid_learning_kotlin.view.wechat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
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

        weChatVm.weChatBloggerLd.observe(viewLifecycleOwner, Observer {
            mWeChatBlogger.clear()
            mWeChatBlogger.addAll(it)
            for (dataBean in it) {
                weChatBloggerTabLayout.addTab(
                    weChatBloggerTabLayout.newTab().setText(dataBean.name)
                )
            }
        })

        weChatVm.weChatArticleListLd.observe(viewLifecycleOwner, Observer {
            mWeChatArticleList.clear()
            mWeChatArticleList.addAll(it.articles)
            val adapter = ArticleListAdapter(mWeChatArticleList, object :
                ArticleListAdapter.AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = it.articles[position].link
                    val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
                    intent.putExtra(ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY, link)
                    startActivity(intent)
                }

                override fun onLongClicked(v: View?, position: Int) {
                }

            })
            weChatArticleListRcy.adapter = adapter
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

        weChatNsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!v.canScrollVertically(1)) {
                pageNumber++
                weChatVm.setWeChatArticleListLd(currentSelectedBloggerId, pageNumber)
            }
        })

    }
}