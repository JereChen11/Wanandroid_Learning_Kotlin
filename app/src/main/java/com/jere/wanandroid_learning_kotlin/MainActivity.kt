package com.jere.wanandroid_learning_kotlin

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
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
import com.jere.wanandroid_learning_kotlin.ui.complete_project.CompleteProjectFragment
import com.jere.wanandroid_learning_kotlin.ui.home.HomeFragment
import com.jere.wanandroid_learning_kotlin.ui.knowledge_system.KnowledgeSystemFragment
import com.jere.wanandroid_learning_kotlin.ui.wechart.WeChartFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home_btn -> addFragment(HomeFragment(), true, "homeBtn")
            R.id.complete_project_btn -> addFragment(
                CompleteProjectFragment(),
                false,
                "completeProject"
            )
            R.id.we_chart_btn -> addFragment(WeChartFragment(), false, "weChart")
            R.id.knowledge_system_btn -> addFragment(
                KnowledgeSystemFragment(),
                false,
                "knowledgeSystemBtn"
            )
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
                R.id.nav_home, R.id.nav_complete_project, R.id.nav_we_chat,
                R.id.nav_knowledge_system, R.id.nav_about_me, R.id.nav_tutorial
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val homeBtn: Button = findViewById(R.id.home_btn)
        val completeProjectBtn: Button = findViewById(R.id.complete_project_btn)
        val weChartBtn: Button = findViewById(R.id.we_chart_btn)
        val knowledgeSystemBtn: Button = findViewById(R.id.knowledge_system_btn)
        homeBtn.setOnClickListener(this)
        completeProjectBtn.setOnClickListener(this)
        weChartBtn.setOnClickListener(this)
        knowledgeSystemBtn.setOnClickListener(this)
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
