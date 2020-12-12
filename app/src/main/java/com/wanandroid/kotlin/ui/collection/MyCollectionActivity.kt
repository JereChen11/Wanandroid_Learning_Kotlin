package com.wanandroid.kotlin.ui.collection

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jaeger.library.StatusBarUtil
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.model.bean.Article
import com.wanandroid.kotlin.utils.BaseActivity
import com.wanandroid.kotlin.ui.ArticleDetailWebViewActivity
import com.wanandroid.kotlin.ui.ArticleListAdapter
import com.wanandroid.kotlin.ui.ArticleListAdapter.AdapterItemClickListener
import com.wanandroid.kotlin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_my_collection.*

class MyCollectionActivity : BaseActivity() {
    private lateinit var myCollectionVm: MyCollectionViewModel
    private var collectionArticleList: ArrayList<Article> = ArrayList()
    private lateinit var articleListAdapter: ArticleListAdapter
    private var pageNumber = 0
    private var isLoadAllArticleData = false

    override fun bindLayout(): Int {
        return R.layout.activity_my_collection
    }

    override fun initView(view: View?) {
        articleListAdapter =
            ArticleListAdapter(collectionArticleList, object : AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = collectionArticleList[position].link

                    val intent = Intent(
                        this@MyCollectionActivity,
                        ArticleDetailWebViewActivity::class.java
                    )
                    intent.putExtra(
                        ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                        link
                    )
                    startActivity(intent)
                }

                override fun onLongClicked(v: View?, position: Int) {

                }

                override fun clickWithoutLogin() {
                    startActivity(Intent(this@MyCollectionActivity, LoginActivity::class.java))
                }

            })

        myCollectionRcy.adapter = articleListAdapter
        myCollectionRcy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoadAllArticleData
                ) {
                    pageNumber++
                    myCollectionVm.setCollectionArticleListLd(pageNumber)
                }
            }
        })

        myCollectionVm = ViewModelProvider(this)[MyCollectionViewModel::class.java]
        myCollectionVm.collectionArticleListLd.observe(this, Observer {

            for (article in it.articles) {
                article.collect = true
                collectionArticleList.add(article)
            }

            isLoadAllArticleData = it.over
            articleListAdapter.setIsLoadAllArticleData(isLoadAllArticleData)
            articleListAdapter.setData(collectionArticleList)
        })

    }

    override fun doBusiness(mContext: Context?) {
        myCollectionVm.setCollectionArticleListLd(pageNumber)
    }

    override fun setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.dark_gray), 0)
    }
}
