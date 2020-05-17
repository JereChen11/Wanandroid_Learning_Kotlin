package com.jere.wanandroid_learning_kotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.CollectionRepository
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean

class ArticleListAdapter(
    articleList: ArrayList<ArticleListBean.DataBean.DatasBean>,
    adapter: AdapterItemClickListener
) :
    RecyclerView.Adapter<ArticleListAdapter.MyViewHolder>() {

    interface AdapterItemClickListener {
        fun onPositionClicked(v: View?, position: Int)
        fun onLongClicked(v: View?, position: Int)
    }

    private var articleList: ArrayList<ArticleListBean.DataBean.DatasBean> = ArrayList()
    private val adapterItemClickListener: AdapterItemClickListener

    init {
        this.articleList = articleList
        this.adapterItemClickListener = adapter
    }

    class MyViewHolder(itemView: View, adapter: AdapterItemClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        private val adapterItemClickListener: AdapterItemClickListener = adapter
        private val containerCl: ConstraintLayout =
            itemView.findViewById(R.id.article_list_item_container_cl)
        val titleTv: TextView = itemView.findViewById(R.id.article_list_item_title_tv)
        val authorTv: TextView = itemView.findViewById(R.id.article_list_item_author_tv)
        val dateTv: TextView = itemView.findViewById(R.id.article_list_item_shared_date_tv)
        val collectionIconIv: ImageView = itemView.findViewById(R.id.collection_icon_iv)

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
        val data: ArticleListBean.DataBean.DatasBean = articleList[position]
        holder.titleTv.text = data.title
        holder.authorTv.text = data.author
        holder.dateTv.text = data.niceShareDate
        if (data.isCollect) {
            holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
        } else {
            holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
        }
        holder.collectionIconIv.setOnClickListener {
            if (data.isCollect) {
                CollectionRepository.unCollectArticle(
                    data.id,
                    object : CollectionRepository.CollectOrUnCollectListener {
                        override fun isSuccessful(isSuccess: Boolean) {
                            if (isSuccess) {
                                holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_unstar)
                                data.isCollect = false
                            }
                        }
                    }
                )
            } else {
                CollectionRepository.collectArticle(
                    data.id,
                    object : CollectionRepository.CollectOrUnCollectListener {
                        override fun isSuccessful(isSuccess: Boolean) {
                            if (isSuccess) {
                                holder.collectionIconIv.setImageResource(R.drawable.vector_drawable_star)
                                data.isCollect = true
                            }
                        }

                    })
            }
        }
    }

}