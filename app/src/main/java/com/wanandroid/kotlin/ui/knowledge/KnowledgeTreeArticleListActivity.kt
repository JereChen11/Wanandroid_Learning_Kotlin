package com.wanandroid.kotlin.ui.knowledge

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.repository.KnowledgeTreeRepository
import com.wanandroid.kotlin.databinding.ActivityKnowledgeSystemArticleListBinding
import com.wanandroid.kotlin.ui.adapter.ArticleListAdapter
import com.wanandroid.kotlin.ui.base.BaseVmVbActivity
import com.wanandroid.kotlin.ui.detail.ArticleDetailWebViewVbActivity

class KnowledgeTreeArticleListActivity :
    BaseVmVbActivity<KnowledgeTreeViewModel, ActivityKnowledgeSystemArticleListBinding>() {

    companion object {
        const val KNOWLEDGE_SYSTEM_CID = "cid"
        const val KNOWLEDGE_SYSTEM_TITLE_NAME = "titleName"
    }

    private lateinit var knowledgeTreeVm: KnowledgeTreeViewModel
    private lateinit var articleListAdapter: ArticleListAdapter
    private var mKnowledgeSystemArticleListData: ArrayList<Article> = ArrayList()
    private var pageNumber = 0
    private var isLoadAllArticleData = false


    override fun setVmFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KnowledgeTreeViewModel(KnowledgeTreeRepository()) as T
            }
        }
    }

    override fun initView() {

        val name: String? = intent.getStringExtra(KNOWLEDGE_SYSTEM_TITLE_NAME)
        val cid: Int = intent.getIntExtra(KNOWLEDGE_SYSTEM_CID, -1)
        binding.knowledgeTreeArticleListAppBar.setTitle(
            name ?: resources.getString(R.string.article_list_cn)
        )

        articleListAdapter = ArticleListAdapter(
            this,
            mKnowledgeSystemArticleListData,
            object :
                ArticleListAdapter.AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = mKnowledgeSystemArticleListData[position].link
                    val intent = Intent(
                        this@KnowledgeTreeArticleListActivity,
                        ArticleDetailWebViewVbActivity::class.java
                    )
                    intent.putExtra(
                        ArticleDetailWebViewVbActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                        link
                    )
                    startActivity(intent)
                }

                override fun onLongClicked(v: View?, position: Int) {
                }

            })
        binding.knowledgeTreeArticleRcy.adapter = articleListAdapter
        binding.knowledgeTreeArticleRcy.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoadAllArticleData
                ) {
                    pageNumber++
                    knowledgeTreeVm.setKnowledgeSystemArticleListLd(pageNumber, cid)
                }
            }
        })

        knowledgeTreeVm = ViewModelProvider(
            this,
            KnowledgeTreeVmFactory(KnowledgeTreeRepository())
        )[KnowledgeTreeViewModel::class.java]
        knowledgeTreeVm.knowledgeSystemArticleListLd.observe(this, Observer {
            mKnowledgeSystemArticleListData.addAll(it.articles)
            isLoadAllArticleData = it.over
            articleListAdapter.setIsLoadAllArticleData(isLoadAllArticleData)
            articleListAdapter.setData(mKnowledgeSystemArticleListData)
        })

        knowledgeTreeVm.setKnowledgeSystemArticleListLd(pageNumber, cid)
    }

}
