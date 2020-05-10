package com.jere.wanandroid_learning_kotlin.view.knowledgesystem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.homebeanfiles.HomeArticleListBean
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem.KnowledgeSystemViewModel

class KnowledgeSystemArticleListActivity : AppCompatActivity() {
    private lateinit var knowledgeSystemVm: KnowledgeSystemViewModel
    private var mKnowledgeSystemArticleListData: ArrayList<HomeArticleListBean.DataBean.DatasBean> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_system_article_list)

        knowledgeSystemVm = ViewModelProvider(this)[KnowledgeSystemViewModel::class.java]

        val titleNameTv: TextView = findViewById(R.id.knowledge_system_article_list_title_tv)
        val articleListRecyclerView: RecyclerView =
            findViewById(R.id.knowledge_system_article_recycler_view)

        val name: String = intent.getStringExtra("titleName")
        val cid: Int = intent.getIntExtra("cid", -1)
        titleNameTv.text = name

        knowledgeSystemVm.knowledgeSystemArticleListLd.observe(this, Observer {
            mKnowledgeSystemArticleListData = it

            val adapter =
                ArticleListAdapter(it, object : ArticleListAdapter.AdapterItemClickListener {
                    override fun onPositionClicked(v: View?, position: Int) {
                        val link: String? = it[position].link
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
            articleListRecyclerView.adapter = adapter
        })

        knowledgeSystemVm.setKnowledgeSystemArticleListLd(cid)

    }

}
