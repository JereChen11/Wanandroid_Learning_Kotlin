package com.jere.wanandroid_learning_kotlin.view.wechat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.ArticleListBean
import com.jere.wanandroid_learning_kotlin.model.wechartbeanfiles.WeChatBloggerList
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.viewmodel.wechat.WeChatViewModel

class WeChatFragment : Fragment() {

    private lateinit var weChatVm: WeChatViewModel
    private var mWeChatBloggerList: ArrayList<WeChatBloggerList.DataBean> = ArrayList()
    private var mWeChatArticleList: ArrayList<ArticleListBean.DataBean.DatasBean> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weChatVm = ViewModelProvider(this)[WeChatViewModel::class.java]
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
            val adapter = ArticleListAdapter(mWeChatArticleList, object :
                ArticleListAdapter.AdapterItemClickListener {
                override fun onPositionClicked(v: View?, position: Int) {
                    val link: String? = it[position].link
                    val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
                    intent.putExtra(ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY, link)
                    startActivity(intent)
                }

                override fun onLongClicked(v: View?, position: Int) {
                }

            })
            weChatArticleRecyclerView.adapter = adapter
        })

        weChatVm.setWeChatBloggerListLd()
        weChatBloggerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val data: WeChatBloggerList.DataBean = mWeChatBloggerList[position]
                weChatVm.setWeChatArticleListLd(data.id, 0)
            }
        })

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
}