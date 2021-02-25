package com.wanandroid.kotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.data.repository.CollectionRepository
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.ui.customview.PullUpRefreshView
import com.wanandroid.kotlin.utils.SpSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListAdapter(
    private var articleList: ArrayList<Article>,
    private val adapterItemClickListener: AdapterItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val normalArticleViewType = 0
    private val bottomViewType = 1
    private var isLoadAllArticleData = false

    interface AdapterItemClickListener {
        fun onPositionClicked(v: View?, position: Int)
        fun onLongClicked(v: View?, position: Int)
        fun clickWithoutLogin()
    }

    fun setData(newArticleList: ArrayList<Article>) {
        this.articleList = newArticleList
        notifyDataSetChanged()
    }

    fun setIsLoadAllArticleData(isLoadAll: Boolean) {
        isLoadAllArticleData = isLoadAll
    }

    inner class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        private val containerCl: ConstraintLayout =
            itemView.findViewById(R.id.articleListItemContainerCl)
        val titleTv: TextView = itemView.findViewById(R.id.articleListItemTitleTv)
        val authorTv: TextView = itemView.findViewById(R.id.articleListItemAuthorTv)
        val dateTv: TextView = itemView.findViewById(R.id.articleListItemSharedDateTv)
        val collectionIconIv: ImageView = itemView.findViewById(R.id.collectionIconIv)

        init {
            containerCl.setOnClickListener(this)
            titleTv.setOnClickListener(this)
            authorTv.setOnClickListener(this)
            dateTv.setOnClickListener(this)
            collectionIconIv.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            adapterItemClickListener.onPositionClicked(v, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            adapterItemClickListener.onLongClicked(v, adapterPosition)
            return true
        }
    }

    inner class BottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pullUpRefreshView: PullUpRefreshView = itemView.findViewById(R.id.bottomViewPullUpRefreshView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == normalArticleViewType) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_view_home_article_list_item, parent, false)
            return ArticleViewHolder(view)
        }
        val bottomView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_view_article_list_adapter_bottom_view, parent, false)
        return BottomViewHolder(bottomView)

    }

    override fun getItemCount(): Int {
        return articleList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == articleList.size) {
            return bottomViewType
        }
        return normalArticleViewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == normalArticleViewType) {
            val articleViewHolder: ArticleViewHolder = holder as ArticleViewHolder
            articleList[position].apply {
                articleViewHolder.titleTv.text = title
                articleViewHolder.authorTv.text = if (author.isNotEmpty()) author else shareUser
                articleViewHolder.dateTv.text = niceShareDate
                if (collect) {
                    articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
                } else {
                    articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                }
                articleViewHolder.collectionIconIv.setOnClickListener {

                    if (!SpSettings.getIsLogin()) {
                        adapterItemClickListener.clickWithoutLogin()
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            val result = withContext(Dispatchers.IO) {
                                if (collect) {
                                    CollectionRepository()
                                        .unCollectArticle(if (originId > 0) originId else id)
                                } else {
                                    CollectionRepository()
                                        .collectArticle(id)
                                }
                            }
                            if (result is BaseResult.Success) {
                                collect = if (collect) {
                                    articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                                    false
                                } else {
                                    articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
                                    true
                                }
                            } else if (result is BaseResult.Error) {
                                Log.e(
                                    "jereTest",
                                    "collect | unCollect Article failed: ${result.exception.message}"
                                )
                            }
                        }
                    }
                }


            }

        } else {
            val bottomViewHolder: BottomViewHolder = holder as BottomViewHolder
            if (isLoadAllArticleData) {
                bottomViewHolder.pullUpRefreshView.showIsLoadAllData()
            } else {
                bottomViewHolder.pullUpRefreshView.showLoadingData()
            }
        }

    }

}