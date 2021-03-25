package com.wanandroid.kotlin.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.kotlin.MyApp
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.repository.CollectionRepository
import com.wanandroid.kotlin.data.repository.base.BaseResult
import com.wanandroid.kotlin.databinding.RecyclerItemViewArticleListAdapterBottomViewBinding
import com.wanandroid.kotlin.databinding.RecyclerItemViewHomeArticleListItemBinding
import com.wanandroid.kotlin.ui.login.LoginActivity
import com.wanandroid.kotlin.utils.SpSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListAdapter(
    private var context: Context,
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
    }

    fun setData(newArticleList: ArrayList<Article>) {
        this.articleList = newArticleList
        notifyDataSetChanged()
    }

    fun setIsLoadAllArticleData(isLoadAll: Boolean) {
        isLoadAllArticleData = isLoadAll
    }

    inner class ArticleViewHolder(private val binding: RecyclerItemViewHomeArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        fun bind(article: Article) {
            binding.apply {
                articleListItemTitleTv.text = article.title
                articleListItemAuthorTv.text =
                    if (article.author.isNotEmpty()) article.author else article.shareUser
                articleListItemSharedDateTv.text = article.niceShareDate
                if (article.collect) {
                    collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
                } else {
                    collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                }
                collectionIconIv.setOnClickListener {
                    if (!SpSettings.getIsLogin()) {
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            val result = withContext(Dispatchers.IO) {
                                if (article.collect) {
                                    CollectionRepository()
                                        .unCollectArticle(if (article.originId > 0) article.originId else article.id)
                                } else {
                                    CollectionRepository()
                                        .collectArticle(article.id)
                                }
                            }
                            if (result is BaseResult.Success) {
                                article.collect = if (article.collect) {
                                    collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                                    false
                                } else {
                                    collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
                                    true
                                }
                            } else if (result is BaseResult.Error) {
                                Toast.makeText(
                                    MyApp.context,
                                    result.exception.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                articleListItemContainerCl.setOnClickListener(this@ArticleViewHolder)
            }
        }

        override fun onClick(v: View?) {
            adapterItemClickListener.onPositionClicked(v, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            adapterItemClickListener.onLongClicked(v, adapterPosition)
            return true
        }
    }

    inner class BottomViewHolder(private val binding: RecyclerItemViewArticleListAdapterBottomViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.bottomViewPullUpRefreshView.apply {
                if (isLoadAllArticleData) {
                    showIsLoadAllData()
                } else {
                    showLoadingData()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == normalArticleViewType) {
            val binding =
                RecyclerItemViewHomeArticleListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ArticleViewHolder(binding)
        }
        val binding = RecyclerItemViewArticleListAdapterBottomViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BottomViewHolder(binding)

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
            articleViewHolder.bind(articleList[position])
        } else {
            val bottomViewHolder: BottomViewHolder = holder as BottomViewHolder
            bottomViewHolder.bind()
        }

    }

}