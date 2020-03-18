package com.jere.wanandroid_learning_kotlin.ui.wechat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatArticleList
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBloggerList
import com.jere.wanandroid_learning_kotlin.ui.complete_project.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.utils.RecyclerItemClickListener

class WeChatFragment : Fragment() {

    private lateinit var weChatVm: WeChatViewModel
    private var mWeChatBloggerList: ArrayList<WeChatBloggerList.DataBean> = ArrayList()
    private var mWeChatArticleList: ArrayList<WeChatArticleList.DataBean.DatasBean> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weChatVm = ViewModelProviders.of(this).get(WeChatViewModel::class.java)
        return inflater.inflate(R.layout.fragment_we_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weChatBloggerVp: ViewPager2 = view.findViewById(R.id.we_chat_blogger_article_vp)
        val weChatArticleRecyclerView: RecyclerView =
            view.findViewById(R.id.we_chat_article_list_recycler_view)

        weChatVm.weChatBloggerListLd.observe(viewLifecycleOwner, Observer {
            mWeChatBloggerList = it
            weChatBloggerVp.adapter = WeChatBloggerVpAdapter(it)
        })

        weChatVm.weChatArticleListLd.observe(viewLifecycleOwner, Observer {
            mWeChatArticleList = it
            weChatArticleRecyclerView.adapter = WeChatArticleListAdapter(it)
        })

        weChatVm.setWeChatBloggerListLd()
        weChatBloggerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val data: WeChatBloggerList.DataBean = mWeChatBloggerList[position]
                weChatVm.setWeChatArticleListLd(data.id, 0)
            }
        })

        weChatArticleRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context,
                weChatArticleRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val link: String? = mWeChatArticleList[position].link
                        val intent = Intent(context, ArticleDetailWebViewActivity::class.java)
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

    }

    class WeChatBloggerVpAdapter(
        weChatBloggerList: ArrayList<WeChatBloggerList.DataBean>
    ) :
        RecyclerView.Adapter<WeChatBloggerVpAdapter.MyViewHolder>() {
        private var weChatBloggerList: ArrayList<WeChatBloggerList.DataBean> = ArrayList()

        init {
            this.weChatBloggerList = weChatBloggerList
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTv: TextView = itemView.findViewById(R.id.we_chart_blogger_list_item_name_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_pager_item_view_we_chat_blogger_list, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return weChatBloggerList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: WeChatBloggerList.DataBean = weChatBloggerList[position]
            holder.nameTv.text = data.name
        }
    }

    class WeChatArticleListAdapter(weChatArticleList: ArrayList<WeChatArticleList.DataBean.DatasBean>) :
        RecyclerView.Adapter<WeChatArticleListAdapter.MyViewHolder>() {
        private var weChatArticleList: ArrayList<WeChatArticleList.DataBean.DatasBean> = ArrayList()

        init {
            this.weChatArticleList = weChatArticleList
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTv: TextView = itemView.findViewById(R.id.article_list_item_title_tv)
            val authorTv: TextView = itemView.findViewById(R.id.article_list_item_author_tv)
            val dateTv: TextView = itemView.findViewById(R.id.article_list_item_shared_date_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_view_home_article_list_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return weChatArticleList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: WeChatArticleList.DataBean.DatasBean = weChatArticleList[position]
            holder.titleTv.text = data.title
            holder.authorTv.text = data.author
            holder.dateTv.text = data.niceShareDate
        }
    }
}