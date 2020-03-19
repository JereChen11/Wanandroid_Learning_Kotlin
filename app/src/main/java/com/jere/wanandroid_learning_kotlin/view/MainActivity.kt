package com.jere.wanandroid_learning_kotlin.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.jere.wanandroid_learning_kotlin.utils.HomeBottomBarItemCustomView
import com.jere.wanandroid_learning_kotlin.view.completeproject.CompleteProjectFragment
import com.jere.wanandroid_learning_kotlin.view.home.HomeFragment
import com.jere.wanandroid_learning_kotlin.view.knowledgesystem.KnowledgeSystemFragment
import com.jere.wanandroid_learning_kotlin.view.wechat.WeChatFragment
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home_bottom_bar_home_item -> {
                addFragment(HomeFragment(), true, "homeBtn")
                toolbar.title = getString(R.string.menu_home)
            }
            R.id.home_bottom_bar_complete_project_item -> {
                addFragment(
                    CompleteProjectFragment(),
                    false,
                    "completeProject"
                )
                toolbar.title = getString(R.string.menu_complete_project)
            }
            R.id.home_bottom_bar_we_chart_item -> {
                addFragment(WeChatFragment(), false, "weChart")
                toolbar.title = getString(R.string.menu_we_chart)
            }
            R.id.home_bottom_bar_knowledge_system_item -> {
                addFragment(
                    KnowledgeSystemFragment(),
                    false,
                    "knowledgeSystemBtn"
                )
                toolbar.title = getString(R.string.menu_knowledge_system)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        val homeBarItem: HomeBottomBarItemCustomView = findViewById(R.id.home_bottom_bar_home_item)
        val completeProjectItem: HomeBottomBarItemCustomView =
            findViewById(R.id.home_bottom_bar_complete_project_item)
        val weChartItem: HomeBottomBarItemCustomView =
            findViewById(R.id.home_bottom_bar_we_chart_item)
        val knowledgeSystemItem: HomeBottomBarItemCustomView =
            findViewById(R.id.home_bottom_bar_knowledge_system_item)
        homeBarItem.setOnClickListener(this)
        completeProjectItem.setOnClickListener(this)
        weChartItem.setOnClickListener(this)
        knowledgeSystemItem.setOnClickListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
