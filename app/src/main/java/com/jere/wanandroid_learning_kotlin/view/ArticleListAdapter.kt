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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListAdapter(
    private var articleList: ArrayList<Article>,
    private val adapterItemClickListener: AdapterItemClickListener
) :
    RecyclerView.Adapter<ArticleListAdapter.MyViewHolder>() {

    interface AdapterItemClickListener {
        fun onPositionClicked(v: View?, position: Int)
        fun onLongClicked(v: View?, position: Int)
    }

    fun setData(newArticleList: ArrayList<Article>) {
        this.articleList = newArticleList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View, adapter: AdapterItemClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        private val adapterItemClickListener: AdapterItemClickListener = adapter
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_view_home_article_list_item, parent, false)
        return MyViewHolder(view, adapterItemClickListener)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: Article = articleList[position]
        holder.titleTv.text = data.title
        holder.authorTv.text = if (data.author.isNotEmpty()) data.author else data.shareUser
        holder.dateTv.text = data.niceShareDate
        if (data.collect) {
            holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
        } else {
            holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
        }
        holder.collectionIconIv.setOnClickListener {
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
                        holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                        data.collect = false
                    } else {
                        holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
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

}