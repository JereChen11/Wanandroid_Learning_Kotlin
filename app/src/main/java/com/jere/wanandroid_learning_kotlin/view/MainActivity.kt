package com.jere.wanandroid_learning_kotlin.view

import android.content.Context
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.utils.BaseActivity
import com.jere.wanandroid_learning_kotlin.utils.HomeBottomBarItemCustomView
import com.jere.wanandroid_learning_kotlin.view.completeproject.CompleteProjectFragment
import com.jere.wanandroid_learning_kotlin.view.home.HomeFragment
import com.jere.wanandroid_learning_kotlin.view.knowledgesystem.KnowledgeSystemFragment
import com.jere.wanandroid_learning_kotlin.view.me.MeFragment
import com.jere.wanandroid_learning_kotlin.view.wechat.WeChatFragment
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.homeBottomBarHomeItem -> {
                addFragment(HomeFragment(), true, "homeBtn")
                toolbar.title = getString(R.string.menu_home)
            }
            R.id.homeBottomBarCompleteProjectItem -> {
                addFragment(
                    CompleteProjectFragment(),
                    false,
                    "completeProject"
                )
                toolbar.title = getString(R.string.menu_complete_project)
            }
            R.id.homeBottomBarWeChartItem -> {
                addFragment(WeChatFragment(), false, "weChart")
                toolbar.title = getString(R.string.menu_we_chart)
            }
            R.id.homeBottomBarKnowledgeSystemItem -> {
                addFragment(
                    KnowledgeSystemFragment(),
                    false,
                    "knowledgeSystemBtn"
                )
                toolbar.title = getString(R.string.menu_knowledge_system)
            }
            R.id.homeBottomBarMeBottomBarItem -> {
                addFragment(MeFragment(), false, "meFragment")
            }
        }
    }

    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.replace(R.id.nav_host_fragment, fragment, tag)
        transaction.commit()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(view: View?) {

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_complete_project,
                R.id.nav_we_chat,
                R.id.nav_knowledge_system,
                R.id.nav_about_me
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        homeBottomBarHomeItem.setOnClickListener(this)
        homeBottomBarCompleteProjectItem.setOnClickListener(this)
        homeBottomBarWeChartItem.setOnClickListener(this)
        homeBottomBarKnowledgeSystemItem.setOnClickListener(this)
        homeBottomBarMeBottomBarItem.setOnClickListener(this)
    }

    override fun doBusiness(mContext: Context?) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
