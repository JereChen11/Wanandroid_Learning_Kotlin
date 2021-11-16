package com.wanandroid.kotlin.ui.login

import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.data.repository.LoginRepository
import com.wanandroid.kotlin.databinding.ActivityLoginBinding
import com.wanandroid.kotlin.ui.base.BaseVmActivity
import com.wanandroid.kotlin.utils.SpSettings

class LoginActivity : BaseVmActivity<LoginViewModel, ActivityLoginBinding>(), View.OnClickListener {

    private var isLogin: Boolean = true
    private var isPasswordEyeOff = true
    private var isRePasswordEyeOff = true
    private lateinit var loginVm: LoginViewModel

    override fun setVmFactory(): ViewModelProvider.Factory = LoginVmFactory(LoginRepository())

    override fun initView() {
        binding.apply {
            loginOrRegisterBtn.setOnClickListener(this@LoginActivity)
            goRegisterTv.setOnClickListener(this@LoginActivity)
            passwordEyeIv.setOnClickListener(this@LoginActivity)
            repasswordEyeIv.setOnClickListener(this@LoginActivity)
        }

        loginVm = ViewModelProvider(this)[LoginViewModel::class.java]
        loginVm.isLoginLdBean.observe(this, Observer {
            if (it.isSuccess) {
                SpSettings.setIsLogin(true)
                SpSettings.setUsername(it.msg)
                showToast(getString(R.string.login_successful_cn))
                finish()
            } else {
                showToast(it.msg)
            }
        })
        loginVm.isRegisterLdBean.observe(this, Observer {
            if (it.isSuccess) {
                SpSettings.setIsLogin(true)
                finish()
                showToast(getString(R.string.register_successful_cn))
            } else {
                showToast(it.msg)
            }
        })

    }


    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                loginOrRegisterBtn -> {
                    val userName = userNameEt.text.toString().trim()
                    val password = passwordEt.text.toString().trim()
                    val rePassword = rePasswordEt.text.toString().trim()
                    if (isLogin) {
                        if (userName.isEmpty() || password.isEmpty()) {
                            showToast(getString(R.string.pls_input_right_username_password_cn))
                            return
                        }
                        loginVm.setIsLoginLd(userName, password)
                    } else {
                        if (userName.isEmpty()
                            || password.isEmpty()
                            || rePassword.isEmpty()
                        ) {
                            showToast(getString(R.string.pls_input_right_username_password_cn))
                            return
                        } else if (rePassword != password) {
                            showToast(getString(R.string.pls_check_password))
                            return
                        }
                        loginVm.setIsRegisterLd(userName, password, rePassword)
                    }
                }
                goRegisterTv -> {
                    if (isLogin) {
                        logInOrRegisterTv.text = getText(R.string.register_cn)
                        loginOrRegisterBtn.text = getText(R.string.register_cn)
                        goRegisterTv.text = getText(R.string.login_cn)
                        rePasswordContainerCl.visibility = View.VISIBLE
                        isLogin = false
                    } else {
                        logInOrRegisterTv.text = getText(R.string.login_cn)
                        loginOrRegisterBtn.text = getText(R.string.login_cn)
                        goRegisterTv.text = getText(R.string.register_cn)
                        rePasswordContainerCl.visibility = View.GONE
                        isLogin = true
                    }
                }
                passwordEyeIv -> {
                    if (isPasswordEyeOff) {
                        passwordEyeIv.setImageResource(R.drawable.vector_drawable_eye)
                        passwordEt.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        isPasswordEyeOff = false
                    } else {
                        passwordEyeIv.setImageResource(R.drawable.vector_drawable_eyeoff)
                        passwordEt.inputType = InputType.TYPE_CLASS_TEXT
                        passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                        isPasswordEyeOff = true
                    }

                }
                repasswordEyeIv -> {
                    if (isRePasswordEyeOff) {
                        repasswordEyeIv.setImageResource(R.drawable.vector_drawable_eye)
                        rePasswordEt.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        isRePasswordEyeOff = false
                    } else {
                        repasswordEyeIv.setImageResource(R.drawable.vector_drawable_eyeoff)
                        rePasswordEt.inputType = InputType.TYPE_CLASS_TEXT
                        rePasswordEt.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                        isRePasswordEyeOff = true
                    }
                }
            }
        }
    }


}
