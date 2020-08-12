package com.jere.wanandroid_learning_kotlin.view.mycollection

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.model.articlebeanfile.Article
import com.jere.wanandroid_learning_kotlin.utils.BaseActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleDetailWebViewActivity
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter
import com.jere.wanandroid_learning_kotlin.view.ArticleListAdapter.AdapterItemClickListener
import com.jere.wanandroid_learning_kotlin.viewmodel.mycollection.MyCollectionViewModel
import kotlinx.android.synthetic.main.activity_my_collection.*

class MyCollectionActivity : BaseActivity() {
    private lateinit var myCollectionVm: MyCollectionViewModel
    private var collectionArticleList: ArrayList<Article> = ArrayList()

    override fun bindLayout(): Int {
        return R.layout.activity_my_collection
    }

    override fun initView(view: View?) {

        myCollectionVm = ViewModelProvider(this)[MyCollectionViewModel::class.java]
        myCollectionVm.collectionArticleListLd.observe(this, Observer {
            collectionArticleList.clear()
            collectionArticleList.addAll(it.articles)
            val articleListAdapter: ArticleListAdapter? =
                ArticleListAdapter(collectionArticleList, object : AdapterItemClickListener {
                    override fun onPositionClicked(v: View?, position: Int) {
                        val link: String? = collectionArticleList[position].link

                        val intent = Intent(
                            this@MyCollectionActivity,
                            ArticleDetailWebViewActivity::class.java
                        )
                        intent.putExtra(
                            ArticleDetailWebViewActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                            link
                        )
                        startActivity(intent)
                    }

                    override fun onLongClicked(v: View?, position: Int) {
                    }

                })

            myCollectionRcy.adapter = articleListAdapter
        })

    }

    override fun doBusiness(mContext: Context?) {
        myCollectionVm.setCollectionArticleListLd(0)
        backIv.setOnClickListener {
            finish()
        }
    }
}
