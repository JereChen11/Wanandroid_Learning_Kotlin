package com.jere.wanandroid_learning_kotlin.view.knowledgesystem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemArticleListBean
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.utils.RecyclerItemClickListener
import com.jere.wanandroid_learning_kotlin.viewmodel.knowledgesystem.KnowledgeSystemViewModel

class KnowledgeSystemArticleListActivity : AppCompatActivity() {
    private lateinit var knowledgeSystemVm: KnowledgeSystemViewModel
    private var mKnowledgeSystemArticleListData: ArrayList<KnowledgeSystemArticleListBean.DataBean.DatasBean> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_system_article_list)

        knowledgeSystemVm =
            ViewModelProviders.of(this).get(KnowledgeSystemViewModel::class.java)

        val titleNameTv: TextView = findViewById(R.id.knowledge_system_article_list_title_tv)
        val articleListRecyclerView: RecyclerView =
            findViewById(R.id.knowledge_system_article_recycler_view)

        val name: String = intent.getStringExtra("titleName")
        val cid: Int = intent.getIntExtra("cid", -1)
        titleNameTv.text = name

        knowledgeSystemVm.knowledgeSystemArticleListLd.observe(this, Observer {
            mKnowledgeSystemArticleListData = it
            articleListRecyclerView.adapter = KnowledgeSystemArticleListAdapter(it)
        })
        articleListRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                articleListRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
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

                    override fun onLongItemClick(view: View?, position: Int) {
                    }


                })
        )

        knowledgeSystemVm.setKnowledgeSystemArticleListLd(cid)

    }

    class KnowledgeSystemArticleListAdapter(knowledgeSystemArticleListData: ArrayList<KnowledgeSystemArticleListBean.DataBean.DatasBean>) :
        RecyclerView.Adapter<KnowledgeSystemArticleListAdapter.MyViewHolder>() {
        private val mKnowledgeSystemArticleListData: ArrayList<KnowledgeSystemArticleListBean.DataBean.DatasBean> =
            knowledgeSystemArticleListData


        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var titleTv: TextView = itemView.findViewById(R.id.article_list_item_title_tv)
            var authorTv: TextView = itemView.findViewById(R.id.article_list_item_author_tv)
            var dateTv: TextView = itemView.findViewById(R.id.article_list_item_shared_date_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_view_home_article_list_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mKnowledgeSystemArticleListData.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: KnowledgeSystemArticleListBean.DataBean.DatasBean =
                mKnowledgeSystemArticleListData[position]
            holder.titleTv.text = data.title
            holder.authorTv.text = data.author
            holder.dateTv.text = data.niceShareDate

        }
    }


}
