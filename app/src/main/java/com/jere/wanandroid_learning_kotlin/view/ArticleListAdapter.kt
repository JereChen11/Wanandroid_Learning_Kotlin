package com.jere.wanandroid_learning_kotlin.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.BaseResult
import com.jere.wanandroid_learning_kotlin.model.CollectionRepository
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
import com.jere.wanandroid_learning_kotlin.utils.PullUpRefreshView
import com.jere.wanandroid_learning_kotlin.utils.Settings
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
            val data: Article = articleList[position]
            val articleViewHolder: ArticleViewHolder = holder as ArticleViewHolder

            articleViewHolder.titleTv.text = data.title
            articleViewHolder.authorTv.text = if (data.author.isNotEmpty()) data.author else data.shareUser
            articleViewHolder.dateTv.text = data.niceShareDate
            if (data.collect) {
                articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
            } else {
                articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
            }
            articleViewHolder.collectionIconIv.setOnClickListener {

                if (!Settings.getIsLogin()) {
                    adapterItemClickListener.clickWithoutLogin()
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        val result = withContext(Dispatchers.IO) {
                            if (data.collect) {
                                CollectionRepository().unCollectArticle(data.id)
                            } else {
                                CollectionRepository().collectArticle(data.id)
                            }
                        }
                        if (result is BaseResult.Success) {
                            if (data.collect) {
                                articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                                data.collect = false
                            } else {
                                articleViewHolder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
                                data.collect = true
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