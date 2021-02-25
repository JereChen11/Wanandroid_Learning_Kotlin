package com.wanandroid.kotlin.ui.knowledge

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jaeger.library.StatusBarUtil
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.repository.KnowledgeTreeRepository
import com.wanandroid.kotlin.ui.adapter.ArticleListAdapter
import com.wanandroid.kotlin.ui.base.BaseActivity
import com.wanandroid.kotlin.ui.detail.ArticleDetailWebViewActivity
import com.wanandroid.kotlin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_knowledge_system_article_list.*

class KnowledgeTreeArticleListActivity : BaseActivity() {

    companion object {
        const val KNOWLEDGE_SYSTEM_CID = "cid"
        const val KNOWLEDGE_SYSTEM_TITLE_NAME = "titleName"
    }

    private lateinit var knowledgeTreeVm: KnowledgeTreeViewModel
    private lateinit var articleListAdapter: ArticleListAdapter
    private var mKnowledgeSystemArticleListData: ArrayList<Article> = ArrayList()
    private var pageNumber = 0
    private var isLoadAllArticleData = false

    override fun bindLayout(): Int {
        return R.layout.activity_knowledge_system_article_list
    }

    override fun initView(view: View?) {

        val name: String? = intent.getStringExtra(KNOWLEDGE_SYSTEM_TITLE_NAME)
        val cid: Int = intent.getIntExtra(KNOWLEDGE_SYSTEM_CID, -1)
        knowledgeTreeArticleListAppBar.setTitle(
            name ?: resources.getString(R.string.article_list_cn)
        )

        articleListAdapter = ArticleListAdapter(
            mKnowledgeSystemArticleListData,
            object :
                ArticleListAdapter.AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = mKnowledgeSystemArticleListData[position].link
                    val intent = Intent(
                        this@KnowledgeTreeArticleListActivity,
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
                    startActivity(
                        Intent(
                            this@KnowledgeTreeArticleListActivity,
                            LoginActivity::class.java
                        )
                    )
                }

            })
        knowledgeTreeArticleRcy.adapter = articleListAdapter
        knowledgeTreeArticleRcy.addOnScrollListener(object :
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

    override fun doBusiness(mContext: Context?) {
    }

    override fun setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.dark_gray), 0)
    }

}
