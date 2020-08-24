package com.jere.wanandroid_learning_kotlin.view.completeproject

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
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
import com.jere.wanandroid_learning_kotlin.utils.RecyclerItemClickListener
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.viewmodel.completeproject.CompleteProjectViewModel
import kotlinx.android.synthetic.main.activity_project_item_list.*
import java.lang.ref.WeakReference


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

        val completeProjectVm: CompleteProjectViewModel =
            ViewModelProvider(this)[CompleteProjectViewModel::class.java]
        completeProjectVm.projectItemListLd.observe(viewLifecycleOwner, Observer {
            mProjectItemListData.addAll(it.articles)
            completeProjectRcy.adapter =
                activity?.let { it1 ->
                    MyAdapter(it1, mProjectItemListData)
                }
        })
        id?.let { completeProjectVm.setProjectItemList(pageNumber, it) }

        completeProjectRcy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    pageNumber++
                    id?.let { completeProjectVm.setProjectItemList(pageNumber, it) }
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

    class MyAdapter(
        activity: Activity,
        projectItems: ArrayList<Article>
    ) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private var projectItems: ArrayList<Article> = ArrayList()
        private var weakReference: WeakReference<Activity>

        init {
            this.projectItems = projectItems
            this.weakReference = WeakReference(activity)
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val envelopIv: ImageView = itemView.findViewById(R.id.envelopIv)
            val titleTv: TextView = itemView.findViewById(R.id.projectItemListTitleTv)
            val describeContentTv: TextView =
                itemView.findViewById(R.id.projectItemListDescribeContentTv)
            val shareDateTv: TextView = itemView.findViewById(R.id.projectItemListShareDateTv)
            val authorTv: TextView = itemView.findViewById(R.id.projectItemListAuthorTv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_view_project_item_list, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return projectItems.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data: Article = projectItems[position]
            weakReference.get()
                ?.let { Glide.with(it).load(data.envelopePic).into(holder.envelopIv) }
            holder.titleTv.text = data.title
            holder.describeContentTv.text = data.desc
            holder.shareDateTv.text = data.niceShareDate
            holder.authorTv.text = data.author
        }
    }
}