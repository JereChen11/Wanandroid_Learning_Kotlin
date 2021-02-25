package com.wanandroid.kotlin.ui.wechat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.repository.WeChatRepository
import com.wanandroid.kotlin.databinding.FragmentWeChatArticleListBinding
import com.wanandroid.kotlin.ui.adapter.ArticleListAdapter
import com.wanandroid.kotlin.ui.base.BaseVmFragment
import com.wanandroid.kotlin.ui.login.LoginActivity

class WeChatArticleListFragment :
    BaseVmFragment<WeChatViewModel, FragmentWeChatArticleListBinding>() {

    private lateinit var articleListAdapter: ArticleListAdapter
    private var weChatArticleList: ArrayList<Article> = ArrayList()
    private var authorId = -1
    private var pageNumber = 0
    private var isLoadAllArticleData = false


    override fun setVmFactory(): ViewModelProvider.Factory = WeChatVmFactory(WeChatRepository())

    override fun initData() {
        arguments?.let {
            authorId = it.getInt(WE_CHAT_AUTHOR_ID_KEY)
            viewModel.setWeChatArticleListLd(authorId)
        }
    }

    override fun initView() {
        articleListAdapter = ArticleListAdapter(
            weChatArticleList,
            object : ArticleListAdapter.AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                }

                override fun onLongClicked(v: View?, position: Int) {
                }

                override fun clickWithoutLogin() {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }

            })
        binding.weChatArticleListRcy.apply {
            adapter = articleListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && !isLoadAllArticleData
                    ) {
                        pageNumber++
                        viewModel.setWeChatArticleListLd(authorId, pageNumber)
                    }
                }
            })
        }
    }

    override fun initObserve() {
        viewModel.weChatArticleListLd.observe(this, Observer {
            weChatArticleList.addAll(it.articles)
            isLoadAllArticleData = it.over
            articleListAdapter.setIsLoadAllArticleData(isLoadAllArticleData)
            articleListAdapter.setData(weChatArticleList)
        })

    }

    companion object {

        const val WE_CHAT_AUTHOR_ID_KEY = "WE_CHAT_AUTHOR_ID"

        fun newInstance(authorId: Int) =
            WeChatArticleListFragment().apply {
                arguments = Bundle().apply {
                    putInt(WE_CHAT_AUTHOR_ID_KEY, authorId)
                }
            }
    }


}