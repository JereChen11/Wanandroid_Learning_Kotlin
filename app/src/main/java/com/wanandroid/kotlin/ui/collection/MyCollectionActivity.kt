package com.wanandroid.kotlin.ui.collection

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.repository.MyCollectionRepository
import com.wanandroid.kotlin.databinding.ActivityMyCollectionBinding
import com.wanandroid.kotlin.ui.adapter.ArticleListAdapter
import com.wanandroid.kotlin.ui.adapter.ArticleListAdapter.AdapterItemClickListener
import com.wanandroid.kotlin.ui.base.BaseVmActivity
import com.wanandroid.kotlin.ui.detail.ArticleDetailWebViewActivity

class MyCollectionActivity : BaseVmActivity<MyCollectionViewModel, ActivityMyCollectionBinding>() {
    private var collectionArticleList: ArrayList<Article> = ArrayList()
    private lateinit var articleListAdapter: ArticleListAdapter
    private var pageNumber = 0
    private var isLoadAllArticleData = false

    override fun setVmFactory(): ViewModelProvider.Factory = MyCollectionVmFactory(
        MyCollectionRepository()
    )

    override fun initData() {
        viewModel.setCollectionArticleListLd(pageNumber)
    }

    override fun initView() {
        articleListAdapter =
            ArticleListAdapter(
                this,
                collectionArticleList,
                object : AdapterItemClickListener {
                    override fun onPositionClicked(v: View?, position: Int) {
                        val link: String = collectionArticleList[position].link

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

                })

        binding.myCollectionRcy.apply {
            adapter = articleListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && !isLoadAllArticleData
                    ) {
                        pageNumber++
                        viewModel.setCollectionArticleListLd(pageNumber)
                    }
                }
            })
        }

        viewModel.collectionArticleListLd.observe(this, Observer {

            for (article in it.articles) {
                article.collect = true
                collectionArticleList.add(article)
            }

            isLoadAllArticleData = it.over
            articleListAdapter.setIsLoadAllArticleData(isLoadAllArticleData)
            articleListAdapter.setData(collectionArticleList)
        })

    }

}
