package com.wanandroid.kotlin.ui.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.model.bean.Article
import com.wanandroid.kotlin.utils.PullUpRefreshView
import com.wanandroid.kotlin.utils.RecyclerItemClickListener
import com.wanandroid.kotlin.ui.ArticleDetailWebViewActivity
import kotlinx.android.synthetic.main.activity_project_item_list.*


class ProjectItemListFragment : Fragment() {

    private var id: Int? = null

    companion object {
        private const val COMPLETE_PROJECT_ID_KEY = "COMPLETE_PROJECT_ID"

        @JvmStatic
        fun newInstance(id: Int) =
            ProjectItemListFragment().apply {
                arguments = Bundle().apply {
                    putInt(COMPLETE_PROJECT_ID_KEY, id)
                }
            }
    }

    private var mProjectItemListData: ArrayList<Article> = ArrayList()
    private var pageNumber = 0
    private var isLoadAllArticleData = false
    private lateinit var projectArticleListAdapter: ProjectArticleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(COMPLETE_PROJECT_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_project_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectArticleListAdapter =
            activity?.let { ProjectArticleListAdapter(it, mProjectItemListData) }!!

        val projectVm: ProjectViewModel =
            ViewModelProvider(this)[ProjectViewModel::class.java]
        projectVm.projectItemListLd.observe(viewLifecycleOwner, Observer {
            mProjectItemListData.addAll(it.articles)
            isLoadAllArticleData = it.over
            projectArticleListAdapter.setIsLoadAllArticleData(isLoadAllArticleData)
            projectArticleListAdapter.setData(mProjectItemListData)
        })
        id?.let { projectVm.setProjectItemList(pageNumber, it) }

        completeProjectRcy.adapter = projectArticleListAdapter
        completeProjectRcy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoadAllArticleData
                ) {
                    pageNumber++
                    id?.let { projectVm.setProjectItemList(pageNumber, it) }
                }
            }
        })

        completeProjectRcy.addOnItemTouchListener(
            RecyclerItemClickListener(context,
                completeProjectRcy,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val link: String? = mProjectItemListData[position].link
                        val intent = Intent(activity, ArticleDetailWebViewActivity::class.java)
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

    class ProjectArticleListAdapter(
        private val activity: Activity,
        private var projectItems: ArrayList<Article>
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val normalProjectArticleType = 0
        private val bottomViewType = 1
        private var isLoadAllArticleData = false

        fun setData(projectItemsData: ArrayList<Article>) {
            this.projectItems = projectItemsData
            notifyDataSetChanged()
        }

        fun setIsLoadAllArticleData(isLoadAllArticleData: Boolean) {
            this.isLoadAllArticleData = isLoadAllArticleData
        }

        class ProjectArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val envelopIv: ImageView = itemView.findViewById(R.id.envelopIv)
            val titleTv: TextView = itemView.findViewById(R.id.projectItemListTitleTv)
            val describeContentTv: TextView =
                itemView.findViewById(R.id.projectItemListDescribeContentTv)
            val shareDateTv: TextView = itemView.findViewById(R.id.projectItemListShareDateTv)
            val authorTv: TextView = itemView.findViewById(R.id.projectItemListAuthorTv)
        }

        class BottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val pullUpRefreshView: PullUpRefreshView =
                itemView.findViewById(R.id.bottomViewPullUpRefreshView)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            if (viewType == normalProjectArticleType) {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_item_view_project_item_list, parent, false)
                return ProjectArticleViewHolder(view)
            }
            val bottomView: View = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.recycler_item_view_article_list_adapter_bottom_view,
                    parent,
                    false
                )
            return BottomViewHolder(bottomView)
        }

        override fun getItemCount(): Int {
            return projectItems.size + 1
        }

        override fun getItemViewType(position: Int): Int {
            if (position == projectItems.size) {
                return bottomViewType
            }
            return normalProjectArticleType
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (holder.itemViewType == normalProjectArticleType) {
                val data: Article = projectItems[position]
                val projectArticleViewHolder = holder as ProjectArticleViewHolder
                Glide.with(activity).load(data.envelopePic)
                    .into(projectArticleViewHolder.envelopIv)
                projectArticleViewHolder.titleTv.text = data.title
                projectArticleViewHolder.describeContentTv.text = data.desc
                projectArticleViewHolder.shareDateTv.text = data.niceShareDate
                projectArticleViewHolder.authorTv.text = data.author
            } else {
                val bottomViewHolder = holder as BottomViewHolder
                if (isLoadAllArticleData) {
                    bottomViewHolder.pullUpRefreshView.showIsLoadAllData()
                } else {
                    bottomViewHolder.pullUpRefreshView.showLoadingData()
                }
            }

        }
    }
}