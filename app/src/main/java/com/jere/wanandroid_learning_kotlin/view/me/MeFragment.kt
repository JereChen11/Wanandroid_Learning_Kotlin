package com.jere.wanandroid_learning_kotlin.view.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

        portraitIv.setOnClickListener(this)
        userNameTv.setOnClickListener(this)
        favoriteItem.setOnClickListener(this)
        loginInOutItem.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        initLoginOutBtn()
        initUsernameAndAvatar()
    }

    private fun initUsernameAndAvatar() {
        userNameTv.text = Settings.getUsername() ?: "username"

        val requestOptions = RequestOptions.circleCropTransform();
        if (Settings.getAvatarUriString().isNullOrBlank()) {
            Glide.with(this)
                .load(R.drawable.head_portrait)
                .apply(requestOptions)
                .into(portraitIv)
        } else {
            Glide.with(this)
                .load(Settings.getAvatarUriString())
                .apply(requestOptions)
                .into(portraitIv)
        }

    }

    private fun initLoginOutBtn() {
        if (Settings.getIsLogin()) {
            loginInOutItem.setTitleText(getString(R.string.logout_cn))
        } else {
            loginInOutItem.setTitleText(getString(R.string.login_cn))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.portraitIv, R.id.userNameTv -> {
                startActivity(Intent(activity, PersonalInfoActivity::class.java))
            }
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
