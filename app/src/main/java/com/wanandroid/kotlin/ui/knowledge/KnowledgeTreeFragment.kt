package com.wanandroid.kotlin.ui.knowledge

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.model.bean.KnowledgeTree
import com.wanandroid.kotlin.model.bean.KnowledgeTreeChildren
import kotlinx.android.synthetic.main.fragment_knowledge_tree.*

class KnowledgeTreeFragment : Fragment() {

    private lateinit var knowledgeTreeVm: KnowledgeTreeViewModel
    private var mKnowledgeTreeList: ArrayList<KnowledgeTree> =
        ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        knowledgeTreeVm = ViewModelProvider(this)[KnowledgeTreeViewModel::class.java]
        return inflater.inflate(R.layout.fragment_knowledge_tree, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        knowledgeTreeVm.knowledgeTreeLd.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                mKnowledgeTreeList.clear()
                mKnowledgeTreeList.addAll(it)
                expandableListView.setAdapter(
                    KnowledgeSystemListAdapter(
                        mKnowledgeTreeList
                    )
                )
            })

        knowledgeTreeVm.setKnowledgeSystemCategoryLd()

        expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val childData: KnowledgeTreeChildren =
                mKnowledgeTreeList[groupPosition].children[childPosition]
            val cid: Int = childData.id
            val name: String? = childData.name
            val intent = Intent(context, KnowledgeTreeArticleListActivity::class.java)
            intent.putExtra(KnowledgeTreeArticleListActivity.KNOWLEDGE_SYSTEM_CID, cid)
            intent.putExtra(KnowledgeTreeArticleListActivity.KNOWLEDGE_SYSTEM_TITLE_NAME, name)
            startActivity(intent)
            true
        }
    }

    class KnowledgeSystemListAdapter(
        groupDataList: ArrayList<KnowledgeTree>
    ) : BaseExpandableListAdapter() {

        private var mGroupDataList: ArrayList<KnowledgeTree> = ArrayList()

        init {
            this.mGroupDataList = groupDataList
        }

        override fun getGroupCount(): Int {
            return mGroupDataList.size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return mGroupDataList[groupPosition].children.size
        }

        override fun getGroup(groupPosition: Int): Any {
            return mGroupDataList[groupPosition]
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            return mGroupDataList[groupPosition].children[childPosition]
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
                .inflate(R.layout.knowledge_tree_group_item_view, parent, false)
            val groupTitleTv = mConvertView.findViewById<TextView>(R.id.groupTitleTv)
            val indicateArrow =
                mConvertView.findViewById<ImageView>(R.id.indicateArrow)
            val groupTitleString: String? = mGroupDataList[groupPosition].name
            if (!TextUtils.isEmpty(groupTitleString)) {
                groupTitleTv.text = groupTitleString
            }
            if (isExpanded) {
                indicateArrow.setImageResource(R.drawable.vector_drawable_arrow_up)
            } else {
                indicateArrow.setImageResource(R.drawable.vector_drawable_arrow_down)
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
                .inflate(R.layout.knowledge_tree_child_item_view, parent, false)
            val childTitleTv = mConvertView.findViewById<TextView>(R.id.childTitleTv)
            val childTitleString: String? =
                mGroupDataList[groupPosition].children[childPosition].name
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