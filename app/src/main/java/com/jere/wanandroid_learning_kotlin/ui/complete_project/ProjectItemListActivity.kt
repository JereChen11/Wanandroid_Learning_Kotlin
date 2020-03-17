package com.jere.wanandroid_learning_kotlin.ui.complete_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.completeproject.ProjectItemList
import com.jere.wanandroid_learning_kotlin.utils.RecyclerItemClickListener
import java.lang.ref.WeakReference

class ProjectItemListActivity : AppCompatActivity() {
    companion object {
        const val PROJECT_TREE_ITEM_ID_KEY = "PROJECT_TREE_ITEM_ID"
    }

    private var mProjectItemListDatas: ArrayList<ProjectItemList.DataBean.DatasBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_item_list)

        val cid = intent.getIntExtra(PROJECT_TREE_ITEM_ID_KEY, 0)

        val projectItemListRecyclerView: RecyclerView =
            findViewById(R.id.project_item_list_recycler_view)

        val completeProjectVm: CompleteProjectViewModel =
            ViewModelProviders.of(this).get(CompleteProjectViewModel::class.java)
        completeProjectVm.projectItemListLd.observe(this, Observer {
            mProjectItemListDatas = it
            projectItemListRecyclerView.adapter = MyAdapter(this, it)
        })
        completeProjectVm.setProjectItemList(0, cid)

        projectItemListRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                projectItemListRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val link: String? = mProjectItemListDatas[position].link
                        val intent = Intent(
                            this@ProjectItemListActivity,
                            ArticleDetailWebViewActivity::class.java
                        )
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
        projectItemListActivity: ProjectItemListActivity,
        projectItems: ArrayList<ProjectItemList.DataBean.DatasBean>
    ) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private var projectItems: ArrayList<ProjectItemList.DataBean.DatasBean> = ArrayList()
        private var weakReference: WeakReference<ProjectItemListActivity>

        init {
            this.projectItems = projectItems
            this.weakReference = WeakReference(projectItemListActivity)
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val envelopIv: ImageView = itemView.findViewById(R.id.project_item_list_envelop_iv)
            val titleTv: TextView = itemView.findViewById(R.id.project_item_list_title_tv)
            val describeContentTv: TextView =
                itemView.findViewById(R.id.project_item_list_describe_content_tv)
            val shareDateTv: TextView = itemView.findViewById(R.id.project_item_list_share_date_tv)
            val authorTv: TextView = itemView.findViewById(R.id.project_item_list_author_tv)
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
            val data: ProjectItemList.DataBean.DatasBean = projectItems[position]
            weakReference.get()
                ?.let { Glide.with(it).load(data.envelopePic).into(holder.envelopIv) }
            holder.titleTv.text = data.title
            holder.describeContentTv.text = data.desc
            holder.shareDateTv.text = data.niceShareDate
            holder.authorTv.text = data.author
        }
    }
}
