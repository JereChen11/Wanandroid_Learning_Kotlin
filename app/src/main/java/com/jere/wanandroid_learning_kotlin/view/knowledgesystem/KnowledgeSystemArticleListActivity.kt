package com.jere.wanandroid_learning_kotlin.view.knowledgesystem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
import com.jere.wanandroid_learning_kotlin.utils.BaseActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem.KnowledgeSystemViewModel
import kotlinx.android.synthetic.main.activity_knowledge_system_article_list.*

class KnowledgeSystemArticleListActivity : BaseActivity() {
    private lateinit var knowledgeSystemVm: KnowledgeSystemViewModel
    private var mKnowledgeSystemArticleListData: ArrayList<Article> =
        ArrayList()

    override fun bindLayout(): Int {
        return R.layout.activity_knowledge_system_article_list
    }

    override fun initView(view: View?) {
        knowledgeSystemVm = ViewModelProvider(this)[KnowledgeSystemViewModel::class.java]

        val name: String = intent.getStringExtra("titleName")
        val cid: Int = intent.getIntExtra("cid", -1)
        knowledgeSystemArticleListTitleTv.text = name

        knowledgeSystemVm.knowledgeSystemArticleListLd.observe(this, Observer {
            mKnowledgeSystemArticleListData.clear()
            mKnowledgeSystemArticleListData.addAll(it.articles)

            val adapter =
                ArticleListAdapter(mKnowledgeSystemArticleListData, object : ArticleListAdapter.AdapterItemClickListener {
                    override fun onPositionClicked(v: View?, position: Int) {
                        val link: String? = mKnowledgeSystemArticleListData[position].link
                        val intent = Intent(
                            this@KnowledgeSystemArticleListActivity,
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
            knowledgeSystemArticleRecyclerView.adapter = adapter
        })

        knowledgeSystemVm.setKnowledgeSystemArticleListLd(cid)
    }

    override fun doBusiness(mContext: Context?) {
    }

}
