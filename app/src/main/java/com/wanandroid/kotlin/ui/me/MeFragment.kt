package com.wanandroid.kotlin.ui.me

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.databinding.FragmentMeBinding
import com.wanandroid.kotlin.ui.base.BaseVmVbFragment
import com.wanandroid.kotlin.ui.collection.MyCollectionActivity
import com.wanandroid.kotlin.ui.login.LoginActivity
import com.wanandroid.kotlin.utils.SpSettings

class MeFragment : BaseVmVbFragment<MeViewModel, FragmentMeBinding>(), View.OnClickListener {

    override fun setVmFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MeViewModel() as T
            }
        }
    }


    override fun onResume() {
        super.onResume()
        initLoginOutBtn()
        initUsernameAndAvatar()
    }

    private fun initUsernameAndAvatar() {
        binding.userNameTv.text = SpSettings.getUsername() ?: "username"

        val requestOptions = RequestOptions.circleCropTransform();
        if (SpSettings.getAvatarUriString().isNullOrBlank()) {
            Glide.with(this)
                .load(R.drawable.default_profile)
                .apply(requestOptions)
                .into(binding.portraitIv)
        } else {
            Glide.with(this)
                .load(SpSettings.getAvatarUriString())
                .apply(requestOptions)
                .into(binding.portraitIv)
        }

    }

    private fun initLoginOutBtn() {
        binding.loginInOutItem.setTitleText(
            if (SpSettings.getIsLogin()) {
                getString(R.string.logout_cn)
            } else {
                getString(R.string.login_cn)
            }
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.portraitIv, R.id.userNameTv -> {
                startActivity(Intent(activity, PersonalInfoActivity::class.java))
            }
            R.id.favoriteItem -> {
                if (SpSettings.getIsLogin()) {
                    startActivity(Intent(activity, MyCollectionActivity::class.java))
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
            R.id.loginInOutItem -> {
                if (SpSettings.getIsLogin()) {
                    binding.loginInOutItem.setTitleText(getString(R.string.login_cn))
                    SpSettings.setIsLogin(false)
                    SpSettings.setUsername("username")
                    binding.userNameTv.text = "username"
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
        }
    }

    override fun initView() {
        initLoginOutBtn()

        binding.apply {
            portraitIv.setOnClickListener(this@MeFragment)
            userNameTv.setOnClickListener(this@MeFragment)
            favoriteItem.setOnClickListener(this@MeFragment)
            loginInOutItem.setOnClickListener(this@MeFragment)
        }


    }

}
