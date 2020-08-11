package com.jere.wanandroid_learning_kotlin.view.me

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.utils.Settings
import com.jere.wanandroid_learning_kotlin.view.login.LoginActivity
import com.jere.wanandroid_learning_kotlin.view.mycollection.MyCollectionActivity
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLoginOutBtn()

        favoriteItem.setOnClickListener(this)
        loginInOutItem.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        initLoginOutBtn()
    }

    private fun initLoginOutBtn() {
        if (Settings.getIsLogin()) {
            loginInOutItem.setTitleText(getString(R.string.logout_cn))
        } else {
            loginInOutItem.setTitleText(getString(R.string.login_cn))
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.favoriteItem -> {
                if (Settings.getIsLogin()) {
                    startActivity(Intent(activity, MyCollectionActivity::class.java))
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
            R.id.loginInOutItem -> {
                if (Settings.getIsLogin()) {
                    loginInOutItem.setTitleText(getString(R.string.logout_cn))
                    Settings.setIsLogin(false)
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
        }
    }

}
