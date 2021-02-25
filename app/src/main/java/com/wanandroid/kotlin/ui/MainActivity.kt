package com.wanandroid.kotlin.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.ui.aboutme.AboutMeActivity
import com.wanandroid.kotlin.ui.base.BaseActivity
import com.wanandroid.kotlin.ui.home.HomeFragment
import com.wanandroid.kotlin.ui.knowledge.KnowledgeTreeFragment
import com.wanandroid.kotlin.ui.me.MeFragment
import com.wanandroid.kotlin.ui.project.ProjectTypeFragment
import com.wanandroid.kotlin.ui.wechat.WeChatFragment
import com.wanandroid.kotlin.utils.SpSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : BaseActivity() {

    companion object {
        const val HOME_TAG = "home"
        const val COMPLETE_PROJECT_TAG = "completeProject"
        const val WECHAT_TAG = "weChat"
        const val KNOWLEDGE_SYSTEM_TAG = "knowledgeSystem"
        const val ME_TAG = "me"
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            ft.replace(R.id.navHostFragment, fragment, tag)
            ft.addToBackStack(tag)
        } else {
            ft.replace(R.id.navHostFragment, fragment, tag)
        }
        ft.commit()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(view: View?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, HomeFragment(), HOME_TAG).commit()
        setNavViewAndToolbar(R.string.menu_home, R.id.nav_home)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, mainDrawerLayout, toolbar, R.string.nav_view_drawer_open,
            R.string.nav_view_drawer_close
        )
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_project,
                R.id.nav_we_chat,
                R.id.nav_knowledge_tree,
                R.id.nav_about_me
            ), mainDrawerLayout
        )

        toolbar.title = getString(R.string.menu_home)

        setAvatarAndNickname()

        navView.setNavigationItemSelectedListener {
            val result: Boolean
            when (it.itemId) {
                R.id.nav_home -> {
                    setBottomNavAndToolbar(R.id.bottom_nav_home, R.string.menu_home)
                    result = true
                }
                R.id.nav_project -> {
                    setBottomNavAndToolbar(R.id.bottom_nav_project, R.string.menu_project)
                    result = true
                }
                R.id.nav_we_chat -> {
                    setBottomNavAndToolbar(R.id.bottom_nav_we_chat, R.string.menu_we_chat)
                    result = true
                }
                R.id.nav_knowledge_tree -> {
                    setBottomNavAndToolbar(
                        R.id.bottom_nav_knowledge_tree,
                        R.string.menu_knowledge_system
                    )
                    result = true
                }
                R.id.nav_me -> {
                    setBottomNavAndToolbar(R.id.bottom_nav_me, R.string.menu_me)
                    result = true
                }
                R.id.nav_about_me -> {
                    startActivity(Intent(this, AboutMeActivity::class.java))
                    result = true
                }
                else -> {
                    result = false
                }
            }

            mainDrawerLayout.closeDrawers()
            result
        }

        homeBottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_home -> {
                    replaceFragment(HomeFragment(), HOME_TAG)
                    setNavViewAndToolbar(R.string.menu_home, R.id.nav_home)
                }
                R.id.bottom_nav_project -> {
                    replaceFragment(
                        ProjectTypeFragment(),
                        COMPLETE_PROJECT_TAG
                    )
                    setNavViewAndToolbar(R.string.menu_project, R.id.nav_project)
                }
                R.id.bottom_nav_we_chat -> {
                    replaceFragment(WeChatFragment(), WECHAT_TAG)
                    setNavViewAndToolbar(R.string.menu_we_chat, R.id.nav_we_chat)
                }
                R.id.bottom_nav_knowledge_tree -> {
                    replaceFragment(
                        KnowledgeTreeFragment(),
                        KNOWLEDGE_SYSTEM_TAG
                    )
                    setNavViewAndToolbar(R.string.menu_knowledge_system, R.id.nav_knowledge_tree)
                }
                R.id.bottom_nav_me -> {
                    replaceFragment(MeFragment(), ME_TAG)
                    setNavViewAndToolbar(R.string.menu_me, R.id.nav_me)
                }
            }
            true
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
//                    val intent = Intent(this, SearchActivity::class.java)
//                    startActivity(intent)
                    true
                }
                else -> false
            }

        }
    }

    private fun setAvatarAndNickname() {
        navView.getHeaderView(0).apply {
            Glide.with(this)
                .load(
                    if (SpSettings.getAvatarUriString().isNullOrBlank())
                        R.drawable.default_profile
                    else
                        SpSettings.getAvatarUriString()
                )
                .circleCrop()
                .into(navAvatarIv)
            navNameTv.text = SpSettings.getUsername()
        }
    }

    override fun onResume() {
        super.onResume()
        setAvatarAndNickname()
    }

    override fun doBusiness(mContext: Context?) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_option_menu, menu)
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            showConfirmQuitDialog()
        } else {
            super.onBackPressed()
            val fragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
            fragment?.tag?.let { setToolBarAndBottomBar(it) }
        }
    }

    private fun setToolBarAndBottomBar(tag: String) {
        when (tag) {
            HOME_TAG -> {
                setNavViewAndToolbar(R.string.menu_home, R.id.nav_home)
                homeBottomNavigationView.selectedItemId = R.id.bottom_nav_home
            }
            COMPLETE_PROJECT_TAG -> {
                setNavViewAndToolbar(R.string.menu_project, R.id.nav_project)
                homeBottomNavigationView.selectedItemId = R.id.bottom_nav_project
            }
            WECHAT_TAG -> {
                setNavViewAndToolbar(R.string.menu_we_chat, R.id.nav_we_chat)
                homeBottomNavigationView.selectedItemId = R.id.bottom_nav_we_chat
            }
            KNOWLEDGE_SYSTEM_TAG -> {
                setNavViewAndToolbar(R.string.menu_knowledge_system, R.id.nav_knowledge_tree)
                homeBottomNavigationView.selectedItemId = R.id.bottom_nav_knowledge_tree
            }
            ME_TAG -> {
                setNavViewAndToolbar(R.string.menu_me, R.id.nav_me)
                homeBottomNavigationView.selectedItemId = R.id.bottom_nav_me
            }
        }
    }

    private fun setNavViewAndToolbar(toolbarTitleId: Int, navViewCheckedItemId: Int) {
        toolbar.title = getString(toolbarTitleId)
        navView.setCheckedItem(navViewCheckedItemId)
    }

    private fun setBottomNavAndToolbar(bottomNavSelectedItemId: Int, toolbarTitleId: Int) {
        toolbar.title = getString(toolbarTitleId)
        homeBottomNavigationView.selectedItemId = bottomNavSelectedItemId
    }

    private fun showConfirmQuitDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.quite_app_prompt))
            .setPositiveButton(
                getString(R.string.yes_cn)
            ) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.no_cn), null)
            .show()
    }

    override fun setStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(
            this,
            findViewById(R.id.mainDrawerLayout),
            ContextCompat.getColor(this, R.color.dark_gray),
            0
        )
    }

}
