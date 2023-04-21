package com.wanandroid.kotlin.ui.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wanandroid.kotlin.data.bean.Article
import com.wanandroid.kotlin.data.repository.ProjectRepository
import com.wanandroid.kotlin.databinding.ActivityProjectItemListBinding
import com.wanandroid.kotlin.databinding.RecyclerItemViewArticleListAdapterBottomViewBinding
import com.wanandroid.kotlin.databinding.RecyclerItemViewProjectItemListBinding
import com.wanandroid.kotlin.ui.adapter.RecyclerItemClickListener
import com.wanandroid.kotlin.ui.base.BaseVmVbFragment
import com.wanandroid.kotlin.ui.detail.ArticleDetailWebViewVbActivity

class ProjectItemListFragment : BaseVmVbFragment<ProjectViewModel, ActivityProjectItemListBinding>() {

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
    private var projectId: Int = 0
    private var pageNumber = 0
    private var isLoadAllArticleData = false
    private lateinit var projectArticleListAdapter: ProjectArticleListAdapter

    override fun setVmFactory(): ViewModelProvider.Factory = ProjectVmFactory(ProjectRepository())

    override fun initData() {
        arguments?.let {
            projectId = it.getInt(COMPLETE_PROJECT_ID_KEY)
            viewModel.setProjectItemList(pageNumber, projectId)
        }
    }

    override fun initView() {
        projectArticleListAdapter = ProjectArticleListAdapter(mProjectItemListData)

        binding.completeProjectRcy.apply {
            adapter = projectArticleListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && !isLoadAllArticleData
                    ) {
                        pageNumber++
                        viewModel.setProjectItemList(pageNumber, projectId)
                    }
                }
            })

            addOnItemTouchListener(
                RecyclerItemClickListener(context,
                    this,
                    object :
                        RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val link: String = mProjectItemListData[position].link
                            val intent = Intent(activity, ArticleDetailWebViewVbActivity::class.java)
                            intent.putExtra(
                                ArticleDetailWebViewVbActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                                link
                            )
                            startActivity(intent)
                        }

                        override fun onLongItemClick(view: View?, position: Int) {

                        }
                    })
            )
        }
    }

    override fun initObserve() {
        viewModel.projectItemListLd.observe(viewLifecycleOwner, Observer {
            mProjectItemListData.addAll(it.articles)
            isLoadAllArticleData = it.over
            projectArticleListAdapter.setIsLoadAllArticleData(isLoadAllArticleData)
            projectArticleListAdapter.setData(mProjectItemListData)
        })
    }

    class ProjectArticleListAdapter(
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

        class ProjectArticleViewHolder(private val binding: RecyclerItemViewProjectItemListBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(article: Article) {
                binding.apply {
                    Glide.with(root.context)
                        .load(article.envelopePic)
                        .into(envelopIv)

                    projectItemListTitleTv.text = article.title
                    projectItemListDescribeContentTv.text = article.desc
                    projectItemListShareDateTv.text = article.niceShareDate
                    projectItemListAuthorTv.text = article.author
                }

            }
        }

        class BottomViewHolder(private val binding: RecyclerItemViewArticleListAdapterBottomViewBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(isLoadAllArticleData: Boolean) {
                binding.bottomViewPullUpRefreshView.apply {
                    if (isLoadAllArticleData) {
                        showIsLoadAllData()
                    } else {
                        showLoadingData()
                    }
                }

            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            if (viewType == normalProjectArticleType) {
                val binding = RecyclerItemViewProjectItemListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProjectArticleViewHolder(binding)
            }
            val binding = RecyclerItemViewArticleListAdapterBottomViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return BottomViewHolder(binding)
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
                projectArticleViewHolder.bind(data)
            } else {
                val bottomViewHolder = holder as BottomViewHolder
                bottomViewHolder.bind(isLoadAllArticleData)
            }

        }
    }
}