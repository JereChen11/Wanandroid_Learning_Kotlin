package com.wanandroid.kotlin.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.databinding.ActivityMainBinding
import com.wanandroid.kotlin.databinding.NavHeaderMainBinding
import com.wanandroid.kotlin.ui.base.BaseVmVbActivity
import com.wanandroid.kotlin.ui.detail.ArticleDetailWebViewVbActivity
import com.wanandroid.kotlin.ui.home.HomeVbFragment
import com.wanandroid.kotlin.ui.knowledge.KnowledgeTreeFragment
import com.wanandroid.kotlin.ui.me.MeFragment
import com.wanandroid.kotlin.ui.project.ProjectTypeFragment
import com.wanandroid.kotlin.ui.wechat.WeChatFragment
import com.wanandroid.kotlin.utils.SpSettings

class MainActivity : BaseVmVbActivity<MainViewModel, ActivityMainBinding>() {

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


    override fun setVmFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, HomeVbFragment(), HOME_TAG).commit()
        setNavViewAndToolbar(R.string.menu_home, R.id.nav_home)

        binding.appBarMainInclude.apply {
            setSupportActionBar(toolbar)

            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                binding.mainDrawerLayout,
                toolbar,
                R.string.nav_view_drawer_open,
                R.string.nav_view_drawer_close
            )
            binding.mainDrawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            toolbar.title = getString(R.string.menu_home)


            homeBottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_nav_home -> {
                        replaceFragment(HomeVbFragment(), HOME_TAG)
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
                        setNavViewAndToolbar(
                            R.string.menu_knowledge_system,
                            R.id.nav_knowledge_tree
                        )
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

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_project,
                R.id.nav_we_chat,
                R.id.nav_knowledge_tree,
                R.id.nav_about_me
            ), binding.mainDrawerLayout
        )

        setAvatarAndNickname()

        binding.navView.setNavigationItemSelectedListener {
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
                    val intent = Intent(this, ArticleDetailWebViewVbActivity::class.java)
                    intent.putExtra(
                        ArticleDetailWebViewVbActivity.ARTICLE_DETAIL_WEB_LINK_KEY,
                        "https://juejin.cn/user/1169536104807399/posts"
                    )
                    startActivity(intent)
                    result = true
                }
                else -> {
                    result = false
                }
            }

            binding.mainDrawerLayout.closeDrawers()
            result
        }
    }

    private fun setAvatarAndNickname() {
        binding.navView.getHeaderView(0).apply {

            //todo 要看一下这么写是否工作。
            val headerMainBinding = NavHeaderMainBinding.inflate(layoutInflater)

            Glide.with(this)
                .load(
                    if (SpSettings.getAvatarUriString().isNullOrBlank())
                        R.drawable.default_profile
                    else
                        SpSettings.getAvatarUriString()
                )
                .circleCrop()
                .into(headerMainBinding.navAvatarIv)
            headerMainBinding.navNameTv.text = SpSettings.getUsername()

        }
    }

    override fun onResume() {
        super.onResume()
        setAvatarAndNickname()
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
        val selectedItemId = when (tag) {
            HOME_TAG -> {
                setNavViewAndToolbar(R.string.menu_home, R.id.nav_home)
                R.id.bottom_nav_home
            }
            COMPLETE_PROJECT_TAG -> {
                setNavViewAndToolbar(R.string.menu_project, R.id.nav_project)
                R.id.bottom_nav_project
            }
            WECHAT_TAG -> {
                setNavViewAndToolbar(R.string.menu_we_chat, R.id.nav_we_chat)
                R.id.bottom_nav_we_chat
            }
            KNOWLEDGE_SYSTEM_TAG -> {
                setNavViewAndToolbar(R.string.menu_knowledge_system, R.id.nav_knowledge_tree)
                R.id.bottom_nav_knowledge_tree
            }
            else -> {
                setNavViewAndToolbar(R.string.menu_me, R.id.nav_me)
                R.id.bottom_nav_me
            }
        }
        binding.appBarMainInclude.homeBottomNavigationView.selectedItemId = selectedItemId
    }

    private fun setNavViewAndToolbar(toolbarTitleId: Int, navViewCheckedItemId: Int) {
        binding.appBarMainInclude.toolbar.title = getString(toolbarTitleId)
        binding.navView.setCheckedItem(navViewCheckedItemId)
    }

    private fun setBottomNavAndToolbar(bottomNavSelectedItemId: Int, toolbarTitleId: Int) {
        binding.appBarMainInclude.apply {
            toolbar.title = getString(toolbarTitleId)
            homeBottomNavigationView.selectedItemId = bottomNavSelectedItemId
        }
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
