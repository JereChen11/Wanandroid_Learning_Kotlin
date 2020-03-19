package com.jere.wanandroid_learning_kotlin.ui.knowledge_system

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.knowledgesystembeanfiles.KnowledgeSystemCategoryBean

class KnowledgeSystemFragment : Fragment() {

    private lateinit var knowledgeSystemVm: KnowledgeSystemViewModel
    private var mKnowledgeSystemCategoryList: ArrayList<KnowledgeSystemCategoryBean.DataBean> =
        ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        knowledgeSystemVm =
            ViewModelProviders.of(this).get(KnowledgeSystemViewModel::class.java)
        return inflater.inflate(R.layout.fragment_knowledge_system, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expandableListView: ExpandableListView = view.findViewById(R.id.expandable_list_view)

        knowledgeSystemVm.knowledgeSystemCategoryLd.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                mKnowledgeSystemCategoryList = it
                expandableListView.setAdapter(KnowledgeSystemListAdapter(it))
            })

        knowledgeSystemVm.setKnowledgeSystemCategoryLd()

        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val childData: KnowledgeSystemCategoryBean.DataBean.ChildrenBean =
                mKnowledgeSystemCategoryList[groupPosition].children!![childPosition]
            val cid: Int = childData.id
            val name: String? = childData.name
            val intent = Intent(context, KnowledgeSystemArticleListActivity::class.java)
            intent.putExtra("cid", cid)
            intent.putExtra("titleName", name)
            startActivity(intent)
            true
        }
    }

    class KnowledgeSystemListAdapter(
        groupDataList: ArrayList<KnowledgeSystemCategoryBean.DataBean>
    ) : BaseExpandableListAdapter() {

        private var mGroupDataList: ArrayList<KnowledgeSystemCategoryBean.DataBean> = ArrayList()

        init {
            this.mGroupDataList = groupDataList
        }

        override fun getGroupCount(): Int {
            return mGroupDataList.size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return mGroupDataList[groupPosition].children?.size!!
        }

        override fun getGroup(groupPosition: Int): Any {
            return mGroupDataList[groupPosition]
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            return mGroupDataList[groupPosition].children?.get(childPosition)!!
        }

        override fun getGroupId(groupPosition: Int): Long {
            return groupPosition.toLong()
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            return childPosition.toLong()
        }

        override fun hasStableIds(): Boolean {
            return false
        }

        override fun getGroupView(
            groupPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            val mConvertView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.knowledge_system_group_item_view, parent, false)
            val groupTitleTv = mConvertView.findViewById<TextView>(R.id.group_title_tv)
            val indicateArrow =
                mConvertView.findViewById<ImageView>(R.id.indicate_arrow)
            val groupTitleString: String? = mGroupDataList[groupPosition].name
            if (!TextUtils.isEmpty(groupTitleString)) {
                groupTitleTv.text = groupTitleString
            }
            if (isExpanded) {
                indicateArrow.setImageResource(R.drawable.arrow_up)
            } else {
                indicateArrow.setImageResource(R.drawable.arrow_down)
            }
            return mConvertView
        }

        override fun getChildView(
            groupPosition: Int,
            childPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            val mConvertView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.knowledge_system_child_item_view, parent, false)
            val childTitleTv = mConvertView.findViewById<TextView>(R.id.child_title_tv)
            val childTitleString: String? =
                mGroupDataList[groupPosition].children?.get(childPosition)?.name
            if (!TextUtils.isEmpty(childTitleString)) {
                childTitleTv.text = childTitleString
            }
            return mConvertView
        }

        override fun isChildSelectable(
            groupPosition: Int,
            childPosition: Int
        ): Boolean {
            return true
        }

    }
}